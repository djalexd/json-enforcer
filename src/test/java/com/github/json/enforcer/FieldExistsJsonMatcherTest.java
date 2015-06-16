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
        JsonMatcher matcher = new FieldExistsJsonMatcher("name", false);
        matcher.match(new MockSpringMvcResult("{name: \"JSON\"}"));
        // assert
    }

    @Test
    public void should_validate_if_field_exists_with_null_value() throws Exception {
        // given
        // when
        JsonMatcher matcher = new FieldExistsJsonMatcher("name", true);
        matcher.match(new MockSpringMvcResult("{\"name\": null}"));
        // then
    }

    @Test(expected = AssertionError.class)
    public void should_throw_assertionError_if_field_is_missing() throws Exception {
        // when
        // then
        JsonMatcher matcher = new FieldExistsJsonMatcher("name", false);
        matcher.match(new MockSpringMvcResult("{ }"));
        // assert
    }
}
