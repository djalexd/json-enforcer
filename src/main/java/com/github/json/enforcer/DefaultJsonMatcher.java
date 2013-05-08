package com.github.json.enforcer;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.fest.assertions.api.Assertions;
import org.springframework.util.ClassUtils;

import java.util.Set;

/**
 * A json matcher implementation. This matcher is supported by its builder.
 *
 * @author alex.dobjanschi
 * @since 12:51 PM 5/8/13
 */
public class DefaultJsonMatcher extends AbstractJsonMatcher {

    private static final Set<Class> SIMPLE_FIELD_CLASSES = ImmutableSet.<Class>of(
            Integer.class, Float.class, Double.class, String.class);


    /**
     * Builder pattern for {@link DefaultJsonMatcher}.
     */
    public static class Builder {

        /**
         * Paths that are required to be simple fields:
         * {  ageOfUniverse: 13 }
         */
        private Set<String> requiredFields;

        /**
         * Paths that are required to be arrays:
         * { numbers: [ 1, 2, 3 ] }
         *
         * <p>These array paths will not check content itself, just
         * the fact that it is an array.</p>
         */
        private Set<String> requiredArrays;

        /**
         * Paths that are required to be objects:
         * { person: { ... } }
         *
         * <p>These object paths will not check content itself, just
         * the fact that it is an object.</p>
         */
        private Set<String> requiredObjects;

        private int status;

        public Builder() {
            this.status(200);
            this.clearFields();
            this.clearArrays();
            this.clearObjects();
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder clearFields() {
            this.requiredFields = Sets.newHashSet();
            return this;
        }

        public Builder fields(String ... fields) {
            return this.fields(Sets.newHashSet(fields));
        }

        public Builder fields(Set<String> fields) {
            Preconditions.checkNotNull(fields);
            this.requiredFields = ImmutableSet.copyOf(fields);
            return this;
        }

        public Builder clearArrays() {
            this.requiredArrays = Sets.newHashSet();
            return this;
        }

        public Builder arrays(Set<String> arrays) {
            Preconditions.checkNotNull(arrays);
            this.requiredArrays = ImmutableSet.copyOf(arrays);
            return this;
        }

        public Builder arrays(String ... arrays) {
            return this.arrays(Sets.newHashSet(arrays));
        }


        public Builder clearObjects() {
            this.requiredObjects = Sets.newHashSet();
            return this;
        }

        public Builder objects(Set<String> objects) {
            Preconditions.checkNotNull(objects);
            this.requiredObjects = ImmutableSet.copyOf(objects);
            return this;
        }

        public Builder objects(String ... objects) {
            return this.objects(Sets.newHashSet(objects));
        }

        public JsonMatcher build() {
            return new DefaultJsonMatcher(status, requiredFields, requiredArrays, requiredObjects);
        }
    }


    private final Set<String> requiredFields;
    private final Set<String> requiredArrays;
    private final Set<String> requiredObjects;

    DefaultJsonMatcher(
            int expectedStatus,
            Set<String> requiredFields,
            Set<String> requiredArrays,
            Set<String> requiredObjects) {

        super(expectedStatus);
        this.requiredFields = ImmutableSet.copyOf(requiredFields);
        this.requiredArrays = ImmutableSet.copyOf(requiredArrays);
        this.requiredObjects = ImmutableSet.copyOf(requiredObjects);
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
                throw new AssertionError(String.format("Expect to find path $.%s", f));
            }
        }
    }


    @VisibleForTesting
    void matchRequiredArrays(String json) throws Exception {

        for (final String a : this.requiredArrays) {
            try {
                JSONArray array = JsonPath.read(json, "$." + a);
/*
                Assertions
                        .assertThat(SIMPLE_FIELD_CLASSES)
                        .contains(obj.getClass())
                        .overridingErrorMessage("Expect path $.%s to be simple field, but found %s", f, ClassUtils.getQualifiedName(obj.getClass()));
*/

            } catch (InvalidPathException e) {
                throw new AssertionError(String.format("Expect to find path $.%s", a));
            }
        }
    }


    @VisibleForTesting
    void matchRequiredObjects(String json) throws Exception {

        for (final String a : this.requiredObjects) {
            try {
                JSONObject obj = JsonPath.read(json, "$." + a);
/*
                Assertions
                        .assertThat(SIMPLE_FIELD_CLASSES)
                        .contains(obj.getClass())
                        .overridingErrorMessage("Expect path $.%s to be simple field, but found %s", f, ClassUtils.getQualifiedName(obj.getClass()));
*/

            } catch (InvalidPathException e) {
                throw new AssertionError(String.format("Expect to find path $.%s", a));
            }
        }
    }

}
