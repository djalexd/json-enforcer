package com.github.json.enforcer.support;

import com.github.json.enforcer.AbstractJsonMatcher;

/**
 * @author alex.dobjanschi
 * @since 10:26 AM 5/9/13
 */
public class ExpectedFieldTypeJsonMatcher extends AbstractJsonMatcher {

    public ExpectedFieldTypeJsonMatcher(int expectedStatus) {
        super(expectedStatus);
    }

    public ExpectedFieldTypeJsonMatcher() {
    }

    @Override
    protected void doMatch(String json) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String message(String path, Object... arguments) {
        return super.message(path, arguments);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
