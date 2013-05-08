package com.github.json.enforcer;

import org.fest.assertions.api.Assertions;
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

    private final int expectedStatus;
    protected AbstractJsonMatcher(int expectedStatus) {
        this.expectedStatus = expectedStatus;
    }
    protected AbstractJsonMatcher() {
        this(200);
    }


    @Override
    public final void match(MvcResult result) throws Exception {

        // Check status
        final int actualStatus = result.getResponse().getStatus();
        Assertions
                .assertThat(actualStatus)
                .isEqualTo(this.expectedStatus)
                .overridingErrorMessage("Expected to find status %d, but found %d", expectedStatus, actualStatus);

        // Check content-type
        Assertions
                .assertThat(result.getResponse().getContentType())
                .containsIgnoringCase("application/json")
                .overridingErrorMessage("Expected to find Content-Type: application/json, but found %s", result.getResponse().getContentType());

        // Finally delegate with actual contents.
        final String contents = result.getResponse().getContentAsString();
        this.doMatch(contents);
    }

    protected abstract void doMatch(String json) throws Exception;
}
