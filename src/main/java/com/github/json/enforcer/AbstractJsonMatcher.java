package com.github.json.enforcer;

import org.springframework.test.web.servlet.MvcResult;

/**
 * A base class implementation that performs some checks:
 * <ul>
 *     <li>status (by default, expects 200).</li>
 *     <li>content-type (only expects application/json)</li>
 * </ul>
 *
 * If these preconditions are met, reads the json response and
 * delegates to {@link #doMatch(String)}.
 *
 * @author alex.dobjanschi
 * @since 12:52 PM 5/8/13
 */
public abstract class AbstractJsonMatcher implements JsonMatcher {

    @Override
    public final void match(MvcResult result) throws Exception {

        // Finally delegate with actual contents.
        final String contents = result.getResponse().getContentAsString();
        this.doMatch(contents);
    }

    @Override
    public void match(String json) throws AssertionError {
        try {
            this.doMatch(json);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    protected abstract void doMatch(String json) throws Exception;

    /**
     * Throws an {@link AssertionError} for the given path. Subclasses may
     * override this to provide a different message.
     * @param path Json path where matcher failed.
     * @param arguments A var-arg array of arguments.
     */
    public void failWithMessage(String path, Object... arguments) {
        throw new AssertionError(path);
    }
}
