package com.github.json.enforcer;

import java.util.Arrays;

/**
 * @author alex.dobjanschi
 * @since 2:00 PM 5/9/13
 */
public class CoreMatchers {

    public static JsonMatcher arraySize(int expectedSize) {
        return new ArraySizeJsonMatcher(expectedSize);
    }

    public static JsonMatcher arrayEmpty() {
        return arraySize(0);
    }

    public static JsonMatcher arrayContents(Object ... objects) {
        return new ArrayContentsJsonMatcher(Arrays.asList(objects));
    }

    public static JsonMatcher arrayContents(int status, Object ... objects) {
        return new ArrayContentsJsonMatcher(status, Arrays.asList(objects));
    }

    public static JsonMatcher fieldValue(String path, Object expected) {
        return new FieldValueJsonMatcher(path, expected);
    }

    public static JsonMatcher fieldExists(String path) {
        return new FieldExistsJsonMatcher(path);
    }

    public static JsonMatcher fieldDoesNotExist(String path) {
        return new FieldMissingJsonMatcher(path);
    }
}
