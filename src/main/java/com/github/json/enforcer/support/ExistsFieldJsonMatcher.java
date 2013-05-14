package com.github.json.enforcer.support;

import com.github.json.enforcer.AbstractJsonMatcher;
import com.google.common.base.Optional;

/**
 * @author alex.dobjanschi
 * @since 10:10 AM 5/9/13
 */
public class ExistsFieldJsonMatcher extends AbstractJsonMatcher {

    private final String field;
    public ExistsFieldJsonMatcher(String field) {
        this.field = field;
    }

    @Override
    protected void doMatch(String json) throws Exception {
        final Optional<String> objectOfPath = readFromPath(json, field);
        if (!objectOfPath.isPresent()) {
            failWithMessage(field);
        }
    }

    @Override
    public void failWithMessage(String path, Object... arguments) {
        throw new AssertionError(String.format("Expected to find path %s", path));
    }
}
