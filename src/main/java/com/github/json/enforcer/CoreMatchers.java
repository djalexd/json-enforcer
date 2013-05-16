package com.github.json.enforcer;

import java.util.Arrays;

/**
 * @author alex.dobjanschi
 * @since 2:00 PM 5/9/13
 */
public class CoreMatchers {

    /**
     * Builds a matcher that will validate an array of exactly
     * <code>expectedSize</code> elements.
     * @param expectedSize Number of elements that are expected in the array.
     * @return
     */
    public static JsonMatcher arraySize(int expectedSize) {
        return new ArraySizeJsonMatcher(expectedSize);
    }

    /**
     * Builds a matcher that will validate an array whose size
     * is between <code>minSize</code> and <code>maxSize</code>.
     * @param minSize
     * @param maxSize
     * @return
     */
    public static JsonMatcher arraySize(int minSize, int maxSize) {
        return new ArraySizeJsonMatcher(minSize, maxSize);
    }

    /**
     * Builds a matcher that will validate an empty array (must
     * be present, otherwise an {@link com.jayway.jsonpath.InvalidPathException}
     * will be thrown; to avoid this, use {@link #fieldExists(String)} matcher before).
     * @return
     */
    public static JsonMatcher arrayEmpty() {
        return arraySize(0);
    }

    /**
     * Builds a matcher that will validate if target array contains
     * all the given objects. This should be used in conjunction with
     * {@link JsonMatcherBuilder#arrayContentMatcher(String, JsonMatcher)}.
     * @param objects
     * @return
     */
    public static JsonMatcher arrayContents(Object ... objects) {
        return new ArrayContentsEqualsJsonMatcher(Arrays.asList(objects));
    }

    /**
     * Builds a matcher that will validate if given path Json value matches
     * the expected object.
     *
     * @param path
     * @param expected
     * @return
     */
    public static JsonMatcher fieldValue(String path, Object expected) {
        return new FieldValueJsonMatcher(path, expected);
    }

    /**
     * Builds a matcher that validates if given json path exists.
     * @param path
     * @return
     */
    public static JsonMatcher fieldExists(String path) {
        return new FieldExistsJsonMatcher(path);
    }

    /**
     * Builds a matcher that validates if given json path is missing.
     * @param path
     * @return
     */
    public static JsonMatcher fieldDoesNotExist(String path) {
        return new FieldMissingJsonMatcher(path);
    }

    /**
     * Builds a matcher that will validate each object in a root array, i.e.:
     * <pre>[ { ... }, { ... } ]</pre>
     *
     * The provided matcher will be used.
     * @param matcher
     * @return
     */
    public static JsonMatcher arrayContentsMatcher(JsonMatcher matcher) {
        return new ArrayContentsJsonMatcher(matcher);
    }
}
