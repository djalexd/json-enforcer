package com.github.json.enforcer;

import com.github.json.enforcer.internal.MockSpringMvcResult;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

/**
 * A json matcher implementation. This matcher is supported by its builder.
 *
 * @author alex.dobjanschi
 * @since 12:51 PM 5/8/13
 */
public class DefaultJsonMatcher extends AbstractJsonMatcher {

    private final Set<String> requiredFields;
    private final Set<String> requiredArrays;
    private final Set<String> requiredObjects;

    private final Map<String, JsonMatcher> fieldMatchers;
    private final Map<String, JsonMatcher> arrayContentMatchers;
    private final Map<String, JsonMatcher> arrayMatchers;
    private final Map<String, JsonMatcher> objectMatchers;

    DefaultJsonMatcher(
            Set<String> requiredFields,
            Set<String> requiredArrays,
            Set<String> requiredObjects,
            Map<String, JsonMatcher> fieldMatchers,
            Map<String, JsonMatcher> arrayMatchers,
            Map<String, JsonMatcher> arrayContentMatchers,
            Map<String, JsonMatcher> objectMatchers) {

        this.requiredFields = new HashSet<String>(requiredFields);
        this.requiredArrays = new HashSet<String>(requiredArrays);
        this.requiredObjects = new HashSet<String>(requiredObjects);

        this.fieldMatchers = new HashMap<String, JsonMatcher>(fieldMatchers);
        this.arrayMatchers = new HashMap<String, JsonMatcher>(arrayMatchers);
        this.arrayContentMatchers = new HashMap<String, JsonMatcher>(arrayContentMatchers);
        this.objectMatchers = new HashMap<String, JsonMatcher>(objectMatchers);
    }

    @Override
    protected void doMatch(String json) throws Exception {
        this.matchRequiredFields(json);
        this.matchRequiredArrays(json);
        this.matchRequiredObjects(json);
    }


    void matchRequiredFields(String json) throws Exception {
        for (final String path : this.requiredFields) {
            new FieldExistsJsonMatcher(path).match(new MockSpringMvcResult(json));
            new FieldSimpleClassMatcher(path).match(new MockSpringMvcResult(json));
        }
    }


    @SuppressWarnings("unused")
    void matchRequiredArrays(String json) throws Exception {

        for (final String a : this.requiredArrays) {
            try {
                JSONArray array = JsonPath.read(json, "$." + a);
            } catch (InvalidPathException e) {
                throwPathAssertionError(a, json);
            }
        }

        // Iterate through all array matchers and validate the contents.
        for (Map.Entry<String, JsonMatcher> a : this.arrayMatchers.entrySet()) {

            JsonMatcher matcherForThisArray = a.getValue();
            try {
                JSONArray array = JsonPath.read(json, "$." + a.getKey());
                matcherForThisArray.match(new MockSpringMvcResult(array.toJSONString()));

            } catch (InvalidPathException e) {
                throwPathAssertionError(a.getKey(), json);
            }

        }

        // Iterate through all array matchers and validate each item.
        for (Map.Entry<String, JsonMatcher> a : this.arrayContentMatchers.entrySet()) {
            JsonMatcher matcherForThisArray = a.getValue();
            try {
                JSONArray array = JsonPath.read(json, "$." + a.getKey());
                for(Object o : array) {
                    final String oneObjectAsJsonString = JSONValue.toJSONString(o);
                    matcherForThisArray.match(new MockSpringMvcResult(oneObjectAsJsonString));
                }

            } catch (InvalidPathException e) {
                throwPathAssertionError(a.getKey(), json);
            }

        }
    }


    @SuppressWarnings("unused")
    void matchRequiredObjects(String json) throws Exception {

        final MvcResult result = new MockSpringMvcResult(json);

        for (final String a : this.requiredObjects) {
            new FieldExistsJsonMatcher(a).match(result);
        }

        for (Map.Entry<String, JsonMatcher> a : this.objectMatchers.entrySet()) {
            JsonMatcher matcherForThisObject = a.getValue();
            try {
                JSONObject object = JsonPath.read(json, "$." + a.getKey());
                matcherForThisObject.match(new MockSpringMvcResult(object.toJSONString()));

            } catch (InvalidPathException e) {
                throwPathAssertionError(a.getKey(), json);
            }
        }
    }

    private void throwPathAssertionError(String path, String json) {
        throw new AssertionError(String.format("Expect to find path $.%s for json '%s'", path, json));
    }

}
