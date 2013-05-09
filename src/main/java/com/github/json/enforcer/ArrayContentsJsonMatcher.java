package com.github.json.enforcer;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.util.List;

/**
 * Matches contents for a given array.
 *
 * @author alex.dobjanschi
 * @since 1:34 PM 5/9/13
 */
public class ArrayContentsJsonMatcher extends AbstractJsonMatcher {

    private final List<Object> objects;

    ArrayContentsJsonMatcher(Object ... objects) {
        this.objects = ImmutableList.copyOf(objects);
    }
    ArrayContentsJsonMatcher(int expectedStatus, Object ... objects) {
        super(expectedStatus);
        this.objects = ImmutableList.copyOf(objects);
    }

    @Override
    protected void doMatch(String json) throws Exception {

        final JSONArray array = JsonPath.read(json, "$.[*]");
        for (Object expected : objects) {
            if (array.indexOf(expected) < 0) {
                throw new AssertionError(this.message(null, expected));
            }
        }
    }

    @Override
    public String message(String path, Object... arguments) {
        Preconditions.checkArgument(arguments.length > 0, "No object specified");
        return String.format("Object %s not found in array", arguments[0]);
    }
}
