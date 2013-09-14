package com.github.json.enforcer;

import org.junit.Test;

/**
 * @author alex.dobjanschi
 * @since 6:12 PM 9/14/13
 */
public class EmptyListJsonMatcherTest {

    @Test
    public void should_match_empty_list() throws Exception {
        // when
        EmptyListJsonMatcher matcher = new EmptyListJsonMatcher();
        // then
        matcher.doMatch("[]");
        // assert
    }

    @Test
    public void should_match_empty_list_with_any_number_of_whitespaces() throws Exception {
        // when
        EmptyListJsonMatcher matcher = new EmptyListJsonMatcher();
        // then
        matcher.doMatch("\n[  ] \t\n");
        // assert
    }

    @Test(expected = AssertionError.class)
    public void should_not_match_a_list_that_is_not_empty() throws Exception {
        // when
        EmptyListJsonMatcher matcher = new EmptyListJsonMatcher();
        // then
        matcher.doMatch("['1', '2']");
        // assert
    }
}
