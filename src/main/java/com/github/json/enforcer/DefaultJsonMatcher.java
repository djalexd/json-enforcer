package com.github.json.enforcer;

import com.github.json.enforcer.support.MockSpringMvcResult;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.fest.assertions.api.Assertions;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.Set;

/**
 * A json matcher implementation. This matcher is supported by its builder.
 *
 * @author alex.dobjanschi
 * @since 12:51 PM 5/8/13
 */
public class DefaultJsonMatcher extends AbstractJsonMatcher {

    private static final Set<Class> SIMPLE_FIELD_CLASSES = ImmutableSet.<Class>of(
            Integer.class, Long.class, Float.class, Double.class, String.class);

    private final Set<String> requiredFields;
    private final Set<String> requiredArrays;
    private final Set<String> requiredObjects;

    private final Map<String, JsonMatcher> fieldMatchers;
    private final Map<String, JsonMatcher> arrayMatchers;
    private final Map<String, JsonMatcher> objectMatchers;

    DefaultJsonMatcher(
            int expectedStatus,
            Set<String> requiredFields,
            Set<String> requiredArrays,
            Set<String> requiredObjects,
            Map<String, JsonMatcher> fieldMatchers,
            Map<String, JsonMatcher> arrayMatchers,
            Map<String, JsonMatcher> objectMatchers) {

        super(expectedStatus);
        this.requiredFields = ImmutableSet.copyOf(requiredFields);
        this.requiredArrays = ImmutableSet.copyOf(requiredArrays);
        this.requiredObjects = ImmutableSet.copyOf(requiredObjects);

        this.fieldMatchers = ImmutableMap.copyOf(fieldMatchers);
        this.arrayMatchers = ImmutableMap.copyOf(arrayMatchers);
        this.objectMatchers = ImmutableMap.copyOf(objectMatchers);
    }

    @Override
    protected void doMatch(String json) throws Exception {
        this.matchRequiredFields(json);
        this.matchRequiredArrays(json);
        this.matchRequiredObjects(json);
    }


    @VisibleForTesting
    void matchRequiredFields(String json) throws Exception {

        for (final String f : this.requiredFields) {
            try {
                Object obj = JsonPath.read(json, "$." + f);
                Assertions
                        .assertThat(SIMPLE_FIELD_CLASSES)
                        .contains(obj.getClass())
                        .overridingErrorMessage("Expect path $.%s to be simple field, but found %s", f, ClassUtils.getQualifiedName(obj.getClass()));

            } catch (InvalidPathException e) {
                throwPathAssertionError(f);
            }
        }
    }


    @SuppressWarnings("unused")
    @VisibleForTesting
    void matchRequiredArrays(String json) throws Exception {

        for (final String a : this.requiredArrays) {
            try {
                JSONArray array = JsonPath.read(json, "$." + a);
            } catch (InvalidPathException e) {
                throwPathAssertionError(a);
            }
        }

        // Iterate through all array matchers and validate the contents.
        for (Map.Entry<String, JsonMatcher> a : this.arrayMatchers.entrySet()) {

            JsonMatcher matcherForThisArray = a.getValue();
            try {
                JSONArray array = JsonPath.read(json, "$." + a.getKey());
                matcherForThisArray.match(new MockSpringMvcResult(array.toJSONString()));
/*
                for(Object o : array) {
                    final String oneObjectAsJsonString = JSONValue.toJSONString(o);
                    matcherForThisArray.match(new MockSpringMvcResult(oneObjectAsJsonString));
                }
*/

            } catch (InvalidPathException e) {
                throwPathAssertionError(a.getKey());
            }

        }
    }


    @SuppressWarnings("unused")
    @VisibleForTesting
    void matchRequiredObjects(String json) throws Exception {

        for (final String a : this.requiredObjects) {
            try {
                JSONObject obj = JsonPath.read(json, "$." + a);
            } catch (InvalidPathException e) {
                throwPathAssertionError(a);
            }
        }

        for (Map.Entry<String, JsonMatcher> a : this.objectMatchers.entrySet()) {
            JsonMatcher matcherForThisObject = a.getValue();
            try {
                JSONObject object = JsonPath.read(json, "$." + a.getKey());
                matcherForThisObject.match(new MockSpringMvcResult(object.toJSONString()));

            } catch (InvalidPathException e) {
                throwPathAssertionError(a.getKey());
            }
        }
    }

    private void throwPathAssertionError(String path) {
        throw new AssertionError(String.format("Expect to find path $.%s", path));
    }

}
