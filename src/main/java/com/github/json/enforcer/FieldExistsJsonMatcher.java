package com.github.json.enforcer;

import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;

/**
 * Checks that a given path exists in json.
 *
 * @author alex.dobjanschi
 * @since 8:27 PM 5/12/13
 */
public class FieldExistsJsonMatcher extends AbstractJsonMatcher {
    private final String path;
    public FieldExistsJsonMatcher(String path) {
        this.path = path;
    }

    @Override
    protected void doMatch(String json) throws Exception {
        try {
            JsonPath.read(json, path);
        } catch (InvalidPathException e) {
            throw new AssertionError(this.message(path));
        }
    }

    @Override
    public String message(String path, Object... arguments) {
        return String.format("Expect to have path '%s'", path);
    }
}
