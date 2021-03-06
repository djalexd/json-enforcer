package com.github.json.enforcer;

import com.github.json.enforcer.internal.InternalBundleReader;
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
        if (array.size() < this.minExpectedSize || array.size() > this.maxExpectedSize) {
            failWithMessage(null, this.minExpectedSize, this.maxExpectedSize, array.size());
        }
    }

    @Override
    public void failWithMessage(String path, Object... args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Requires 3 arguments");
        }
        throw new AssertionError(InternalBundleReader.getMessageAndFormat(
                "arraySizeMatcherError", path, args[0], args[1], args[2] ));
    }

}
