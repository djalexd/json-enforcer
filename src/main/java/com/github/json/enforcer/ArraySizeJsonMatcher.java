package com.github.json.enforcer;

import com.google.common.base.Preconditions;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

/**
 * @author alex.dobjanschi
 * @since 6:06 PM 5/9/13
 */
public class ArraySizeJsonMatcher extends AbstractJsonMatcher {

    private final int expectedSize;
    ArraySizeJsonMatcher(int expectedStatus, int expectedSize) {
        super(expectedStatus);
        this.expectedSize = expectedSize;
    }

    ArraySizeJsonMatcher(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    @Override
    protected void doMatch(String json) throws Exception {

        final JSONArray array = JsonPath.read(json, "$.[*]");
        if (array.size() != this.expectedSize) {
            throw new AssertionError(this.message(null, expectedSize, array.size()));
        }
    }

    @Override
    public String message(String path, Object... arguments) {
        Preconditions.checkArgument(arguments.length > 1, "Requires 2 arguments");
        return String.format("Bad array size: expected %d, but found %d", arguments[0], arguments[1]);
    }

}
