package com.github.json.enforcer;

import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;

/**
 * Checks that a given path is missing. Will fail if the path exists.
 *
 * @author alex.dobjanschi
 * @since 8:29 PM 5/12/13
 */
public class FieldMissingJsonMatcher extends AbstractJsonMatcher {
    private final String path;
    public FieldMissingJsonMatcher(String path) {
        this.path = path;
    }

    @Override
    protected void doMatch(String json) throws Exception {
        try {
            JsonPath.read(json, path);
            throw new AssertionError(this.message(path));
        } catch (InvalidPathException e) {
            // This is ok.
        }
    }

    @Override
    public String message(String path, Object... arguments) {
        return String.format("Expect to miss path '%s', but was found", path);
    }
}
