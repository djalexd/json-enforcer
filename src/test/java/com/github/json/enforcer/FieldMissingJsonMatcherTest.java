package com.github.json.enforcer;

import com.github.json.enforcer.internal.MockSpringMvcResult;
import org.junit.Test;

/**
 * @author alex.dobjanschi
 * @since 11:12 AM 5/14/13
 */
public class FieldMissingJsonMatcherTest extends BaseJsonMatcherTest {

    @Test
    public void should_validate_if_field_is_missing() throws Exception {
        // when
        // then
        JsonMatcher matcher = new FieldMissingJsonMatcher("age");
        matcher.match(new MockSpringMvcResult("{ }"));
        // assert
    }

    @Test(expected = AssertionError.class)
    public void should_throw_assertionError_if_field_is_present() throws Exception {
        // when
        // then
        JsonMatcher matcher = new FieldMissingJsonMatcher("age");
        matcher.match(new MockSpringMvcResult("{ age: 20 }"));
        // assert
    }
}
