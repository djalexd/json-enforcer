package com.github.json.enforcer;

import com.github.json.enforcer.internal.InternalBundleReader;
import com.jayway.jsonpath.*;

/**
 * Checks that a given path is missing. Will fail if the path exists.
 *
 * @author alex.dobjanschi
 * @since 8:29 PM 5/12/13
 */
public class FieldMissingJsonMatcher extends AbstractJsonMatcher {

    private static final Configuration cfg = Configuration
            .builder()
            .options(Option.REQUIRE_PROPERTIES)
            .build();

    private final String path;
    public FieldMissingJsonMatcher(String path) {
        this.path = path;
    }

    @Override
    protected void doMatch(String json) throws Exception {
        try {
            DocumentContext ctx = JsonPath.parse(json, cfg);
            ctx.read(path);

            failWithMessage(path);
        } catch (InvalidPathException e) {
            // This is ok.
        }
    }

    @Override
    public void failWithMessage(String path, Object... arguments) {
        throw new AssertionError(
                InternalBundleReader.getMessageAndFormat("fieldMissingMatcherError", path));
    }
}
