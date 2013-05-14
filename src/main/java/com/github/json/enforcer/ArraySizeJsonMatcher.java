package com.github.json.enforcer;

import com.google.common.base.Preconditions;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

/**
 * Matcher that validates array size.
 *
 * @author alex.dobjanschi
 * @since 6:06 PM 5/9/13
 */
public class ArraySizeJsonMatcher extends AbstractJsonMatcher {

    private final int minExpectedSize;
    private final int maxExpectedSize;

    ArraySizeJsonMatcher(int expectedSize) {
        this(expectedSize, expectedSize);
    }

    ArraySizeJsonMatcher(int minExpectedSize, int maxExpectedSize) {
        this.minExpectedSize = minExpectedSize;
        this.maxExpectedSize = maxExpectedSize;
    }


    @Override
    protected void doMatch(String json) throws Exception {

        final JSONArray array = JsonPath.read(json, "$.[*]");
        if (array.size() < this.minExpectedSize) {
            failWithMessage(null, this.minExpectedSize, this.maxExpectedSize, array.size());
        }
    }

    @Override
    public void failWithMessage(String path, Object... arguments) {
        Preconditions.checkArgument(arguments.length > 2, "Requires 3 arguments");
        throw new AssertionError(String.format(
                "Expected array size between %d and %d, but found %d",
                arguments[0], arguments[1], arguments[2]));
    }

}
