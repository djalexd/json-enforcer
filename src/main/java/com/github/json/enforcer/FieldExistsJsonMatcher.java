package com.github.json.enforcer;

import com.github.json.enforcer.internal.InternalBundleReader;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

/**
 * Checks that a given path exists in json.
 *
 * @author alex.dobjanschi
 * @since 8:27 PM 5/12/13
 */
public class FieldExistsJsonMatcher extends AbstractJsonMatcher {

    private static final Configuration cfg = Configuration
            .builder()
            .options(Option.REQUIRE_PROPERTIES)
            .build();

    private final String path;
    private final boolean allowNullValue;

    public FieldExistsJsonMatcher(String path, boolean allowNullValue) {
        this.path = path;
        this.allowNullValue = allowNullValue;
    }

    @Override
    protected void doMatch(String json) throws Exception {
        try {
            DocumentContext ctx = JsonPath.parse(json, cfg);
            ctx.read("$." + path);

        } catch (InvalidPathException e) {
            if (!allowNullValue) {
                failWithMessage(path);
            }
        }
    }

    @Override
    public void failWithMessage(String path, Object... arguments) {
        throw new AssertionError(InternalBundleReader.getMessageAndFormat(
                "fieldExistsMatcherError", path));
    }
}
