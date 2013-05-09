package com.github.json.enforcer;

import org.springframework.test.web.servlet.ResultMatcher;

/**
 * A thin wrapper that provides binding for multiple libraries.
 *
 * <p>For now, only <strong>Spring MVC</strong> is available.</p>
 *
 * @author alex.dobjanschi
 * @since 11:50 AM 5/8/13
 */
public interface JsonMatcher extends ResultMatcher {

    /**
     * Provides a descriptive message for the AssertionError
     * that is usually thrown when this matcher fails.
     * @param path
     * @return
     */
    String message(String path, Object ... arguments);
}
