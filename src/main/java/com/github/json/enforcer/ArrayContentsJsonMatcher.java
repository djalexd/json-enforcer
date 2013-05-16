package com.github.json.enforcer;

import com.github.json.enforcer.internal.MockSpringMvcResult;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

/**
 * Matches each item in a root json context against a given matcher.
 *
 * @author alex.dobjanschi
 * @since 1:34 PM 5/9/13
 */
public class ArrayContentsJsonMatcher extends AbstractJsonMatcher {

    private final JsonMatcher matcher;

    ArrayContentsJsonMatcher(JsonMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    protected void doMatch(String json) throws Exception {

        final JSONArray array = JsonPath.read(json, "$.[*]");
        for (Object expected : array) {
            matcher.match( new MockSpringMvcResult(expected.toString()) );
        }
    }
}
