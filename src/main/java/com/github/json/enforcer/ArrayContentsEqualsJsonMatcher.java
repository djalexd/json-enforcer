package com.github.json.enforcer;

import com.github.json.enforcer.internal.InternalBundleReader;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Matches contents for a given array.
 *
 * @author alex.dobjanschi
 * @since 1:34 PM 5/9/13
 */
public class ArrayContentsEqualsJsonMatcher extends AbstractJsonMatcher {

    private final List<Object> objects;

    ArrayContentsEqualsJsonMatcher(List<Object> objects) {
        this.objects = new ArrayList<Object>(objects);
    }

    @Override
    protected void doMatch(String json) throws Exception {

        final JSONArray array = JsonPath.read(json, "$.[*]");
        for (Object expected : objects) {
            if (array.indexOf(expected) < 0) {
                this.failWithMessage(null, expected);
            }
        }
    }

    @Override
    public void failWithMessage(String path, Object... arguments) {
        if (arguments == null || arguments.length == 0) {
            throw new IllegalArgumentException("No object specified");
        }
        throw new AssertionError(InternalBundleReader.getMessageAndFormat(
                "arrayContentsMatcherError", path, arguments[0]));
    }
}
