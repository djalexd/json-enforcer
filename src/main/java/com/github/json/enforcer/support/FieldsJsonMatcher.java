package com.github.json.enforcer.support;

import com.github.json.enforcer.AbstractJsonMatcher;
import com.github.json.enforcer.JsonMatcher;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.*;
import org.fest.assertions.api.Assertions;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.Set;

/**
 * @author alex.dobjanschi
 * @since 10:37 PM 5/8/13
 */
@SuppressWarnings("unused")
public class FieldsJsonMatcher extends AbstractJsonMatcher {

    private static final Set<Class> SIMPLE_FIELD_CLASSES = ImmutableSet.<Class>of(
            Integer.class, Float.class, Double.class, String.class);

    private final Multimap<String, JsonMatcher> fieldMatchers;

    public FieldsJsonMatcher(int status,
            Set<String> requiredFields,
            Map<String, JsonMatcher> fieldMatchers) {
        super(status);
        this.fieldMatchers = matchers(requiredFields, fieldMatchers);
    }

    static Multimap<String, JsonMatcher> matchers(
            Set<String> requiredFields,
            Map<String, JsonMatcher> fieldMatchers) {

        Map<String, JsonMatcher> exists =
                Maps.asMap(requiredFields, new Function<String, JsonMatcher>() {
                    @Override
                    public JsonMatcher apply(String input) {
                        return new ExistsFieldJsonMatcher(input);
                    }
                });

        Multimap<String, JsonMatcher> allMatchers = Multimaps.forMap(exists);
        allMatchers.putAll(Multimaps.forMap(fieldMatchers));

        return allMatchers;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doMatch(String json) throws Exception {

        for (Map.Entry<String, JsonMatcher> f : this.fieldMatchers.entries()) {
            final MockSpringMvcResult result = new MockSpringMvcResult(json);
            f.getValue().match(result);
        }

/*        for (final String f : this.requiredFields) {

            Optional<Object> obj = this.readFromPath(json, "$." + f);
            if (!obj.isPresent()) {
                throw new AssertionError(String.format("Expect to find path $.%s", f));
            }

            Assertions.assertThat(SIMPLE_FIELD_CLASSES)
                    .contains(obj.get().getClass())
                    .overridingErrorMessage("Expect path $.%s to be simple field, but found %s", f, ClassUtils.getQualifiedName(obj.get().getClass()));
        }

        // Validate with field matchers
        for (final Map.Entry<String, JsonMatcher> m : this.fieldMatchers.entrySet()) {

            Optional<Object> obj = this.readFromPath(json, "$." + m.getKey());
            if (!obj.isPresent()) {
                throw new AssertionError(String.format("Expect to find path $.%s", m.getKey()));
            }

            m.getValue().match(new MockSpringMvcResult(obj.get().toString()));
        }*/
    }

}