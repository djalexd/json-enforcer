package com.github.json.enforcer;

/**
 * Matches an empty list, <code>[]</code>. Any number of whitespaces are allowed.
 *
 * @author alex.dobjanschi
 * @since 5:58 PM 9/14/13
 */
public class EmptyListJsonMatcher extends AbstractJsonMatcher {
    @Override
    protected void doMatch(String json) throws Exception {
        if (!json.matches("\\W*\\[\\W*\\]\\W*")) {
            failWithMessage("JSON is not an empty list");
        }
    }
}
