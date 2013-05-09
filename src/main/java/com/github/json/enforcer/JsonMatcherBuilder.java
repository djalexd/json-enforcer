package com.github.json.enforcer;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

/**
 * Builder pattern for {@link com.github.json.enforcer.DefaultJsonMatcher}.
 */
@SuppressWarnings("unused")
public class JsonMatcherBuilder {

    /**
     * Paths that are required to be simple fields:
     * {  ageOfUniverse: 13 }
     */
    private Set<String> requiredFields;

    private Map<String, JsonMatcher> fieldMatchers;

    /**
     * Paths that are required to be arrays:
     * { numbers: [ 1, 2, 3 ] }
     *
     * <p>These array paths will not check content itself, just
     * the fact that it is an array.</p>
     */
    private Set<String> requiredArrays;

    private Map<String, JsonMatcher> arrayMatchers;

    /**
     * Paths that are required to be objects:
     * { person: { ... } }
     *
     * <p>These object paths will not check content itself, just
     * the fact that it is an object.</p>
     */
    private Set<String> requiredObjects;

    private Map<String, JsonMatcher> objectMatchers;

    private int status;

    public JsonMatcherBuilder() {
        this.status(200);
        this.clearFields();
        this.clearFieldMatchers();
        this.clearArrays();
        this.clearArrayMatchers();
        this.clearObjects();
        this.clearObjectMatchers();
    }

    /**
     * Ignore status in the {@link JsonMatcher} implementations.
     * @return
     */
    public JsonMatcherBuilder ignoreStatus() {
        this.status = -1;
        return this;
    }

    public JsonMatcherBuilder status(int status) {
        this.status = status;
        return this;
    }

    public JsonMatcherBuilder clearFields() {
        this.requiredFields = Sets.newHashSet();
        return this;
    }

    public JsonMatcherBuilder fields(String ... fields) {
        return this.fields(Sets.newHashSet(fields));
    }

    public JsonMatcherBuilder fields(Set<String> fields) {
        Preconditions.checkNotNull(fields);
        this.requiredFields = ImmutableSet.copyOf(fields);
        return this;
    }

    public JsonMatcherBuilder clearArrays() {
        this.requiredArrays = Sets.newHashSet();
        return this;
    }

    public JsonMatcherBuilder arrays(Set<String> arrays) {
        Preconditions.checkNotNull(arrays);
        this.requiredArrays = ImmutableSet.copyOf(arrays);
        return this;
    }

    public JsonMatcherBuilder arrays(String ... arrays) {
        return this.arrays(Sets.newHashSet(arrays));
    }


    public JsonMatcherBuilder clearObjects() {
        this.requiredObjects = Sets.newHashSet();
        return this;
    }

    public JsonMatcherBuilder objects(Set<String> objects) {
        Preconditions.checkNotNull(objects);
        this.requiredObjects = ImmutableSet.copyOf(objects);
        return this;
    }

    public JsonMatcherBuilder objects(String ... objects) {
        return this.objects(Sets.newHashSet(objects));
    }

    public JsonMatcherBuilder clearFieldMatchers() {
        this.fieldMatchers = Maps.newHashMapWithExpectedSize(32);
        return this;
    }

    public JsonMatcherBuilder fieldMatcher(String field, JsonMatcher jsonMatcher) {
        this.fieldMatchers.put(field, jsonMatcher);
        return this;
    }

    public JsonMatcherBuilder clearArrayMatchers() {
        this.arrayMatchers = Maps.newHashMapWithExpectedSize(32);
        return this;
    }

    public JsonMatcherBuilder arrayMatcher(String field, JsonMatcher jsonMatcher) {
        this.arrayMatchers.put(field, jsonMatcher);
        return this;
    }

    public JsonMatcherBuilder clearObjectMatchers() {
        this.objectMatchers = Maps.newHashMapWithExpectedSize(32);
        return this;
    }

    public JsonMatcherBuilder objectMatcher(String field, JsonMatcher jsonMatcher) {
        this.objectMatchers.put(field, jsonMatcher);
        return this;
    }



    public JsonMatcher build() {
        return new DefaultJsonMatcher(status, requiredFields, requiredArrays, requiredObjects, fieldMatchers, arrayMatchers, objectMatchers);
    }
}
