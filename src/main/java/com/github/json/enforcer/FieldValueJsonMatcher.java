package com.github.json.enforcer;

import com.jayway.jsonpath.JsonPath;

/**
 * @author alex.dobjanschi
 * @since 11:44 PM 5/10/13
 */
public class FieldValueJsonMatcher extends AbstractJsonMatcher {

    private final Object expectedValue;
    private final String path;

    FieldValueJsonMatcher(String path, Object expectedValue) {
        this.expectedValue = expectedValue;
        this.path = path;
    }

    @Override
    protected void doMatch(String json) throws Exception {
        final Object found = JsonPath.read(json, "$." + path);
        if (!expectedValue.equals(found)) {
            throw new AssertionError("Expected to find " + expectedValue + " for path " + path + "; found " + found);
        }
    }
}
