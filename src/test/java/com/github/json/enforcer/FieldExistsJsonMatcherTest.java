package com.github.json.enforcer;

import com.github.json.enforcer.internal.MockSpringMvcResult;
import org.junit.Test;

/**
 * @author alex.dobjanschi
 * @since 11:15 AM 5/14/13
 */
public class FieldExistsJsonMatcherTest extends BaseJsonMatcherTest {

    @Test
    public void should_validate_if_field_exists() throws Exception {
        // when
        // then
        JsonMatcher matcher = new FieldExistsJsonMatcher("name");
        matcher.match(new MockSpringMvcResult("{name: \"JSON\"}"));
        // assert
    }

    @Test(expected = AssertionError.class)
    public void should_throw_assertionError_if_field_is_missing() throws Exception {
        // when
        // then
        JsonMatcher matcher = new FieldExistsJsonMatcher("name");
        matcher.match(new MockSpringMvcResult("{ }"));
        // assert
    }
}
