package com.github.json.enforcer.internal;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;

/**
 * Implementation of {@link org.springframework.test.web.servlet.MvcResult} that accepts a String
 * as response. All other methods throw exception, do not use!
 *
 * @author alex.dobjanschi
 * @since 11:52 AM 5/8/13
 */
public class MockSpringMvcResult implements MvcResult {

    private final String responseAsString;
    public MockSpringMvcResult(String responseAsString) {
        this.responseAsString = responseAsString;
    }

    @Override
    public MockHttpServletRequest getRequest() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MockHttpServletResponse getResponse() {

        final MockHttpServletResponse response = new MockHttpServletResponse();
        response.setContentType("application/json");
        try {
            response.getWriter().write(this.responseAsString);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("This system does not support required encoding", e);
        }

        return response;
    }

    @Override
    public Object getHandler() {
        throw new UnsupportedOperationException();
    }

    @Override
    public HandlerInterceptor[] getInterceptors() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ModelAndView getModelAndView() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Exception getResolvedException() {
        throw new UnsupportedOperationException();
    }

    @Override
    public FlashMap getFlashMap() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getAsyncResult() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getAsyncResult(long timeout) {
        throw new UnsupportedOperationException();
    }
}