package com.github.json.enforcer;

import com.github.json.enforcer.internal.MockSpringMvcResult;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONUtil;

import java.util.Map;

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

        DocumentContext docCtx = JsonPath.parse(json);

        final JSONArray array = docCtx.read("$.[*]", JSONArray.class);
        for (Object expected : array) {
            Map map = (Map) expected;
            matcher.match( new MockSpringMvcResult(new JSONObject(map).toJSONString()));
        }
    }
}
