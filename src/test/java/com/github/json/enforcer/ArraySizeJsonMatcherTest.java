package com.github.json.enforcer;

import com.github.json.enforcer.internal.MockSpringMvcResult;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.Set;

/**
 * @author alex.dobjanschi
 * @since 11:17 AM 5/14/13
 */
public class ArraySizeJsonMatcherTest extends BaseJsonMatcherTest {

    @Test
    public void should_validate_if_arraySize_between_minSize_and_maxSize() throws Exception {
        // when
        Set<Integer> numbers = ImmutableSet.of(1, 2, 3, 4, 5);
        // then
        JsonMatcher matcher = new ArraySizeJsonMatcher(3, 10);
        matcher.match(new MockSpringMvcResult(objectMapper.writeValueAsString(numbers)));
        // assert
    }

    @Test
    public void should_validate_exact_size() throws Exception {
        // when
        Set<Integer> numbers = ImmutableSet.of(1, 2);
        // then
        JsonMatcher matcher = new ArraySizeJsonMatcher(2);
        matcher.match(new MockSpringMvcResult(objectMapper.writeValueAsString(numbers)));
        // assert
    }

    @Test
    public void should_validate_empty_array() throws Exception {
        // when
        // then
        JsonMatcher matcher = new ArraySizeJsonMatcher(0);
        matcher.match(new MockSpringMvcResult("[]"));
        // assert
    }

    @Test(expected = AssertionError.class)
    public void should_throw_assertError_if_size_below_minSize() throws Exception {
        // when
        Set<Integer> numbers = ImmutableSet.of(1, 2);
        // then
        JsonMatcher matcher = new ArraySizeJsonMatcher(5, 10);
        matcher.match(new MockSpringMvcResult(objectMapper.writeValueAsString(numbers)));
        // assert
    }

    @Test(expected = AssertionError.class)
    public void should_throw_assertError_if_size_above_maxSize() throws Exception {
        // when
        Set<Integer> numbers = ImmutableSet.of(1, 2, 3, 4, 5);
        // then
        JsonMatcher matcher = new ArraySizeJsonMatcher(1, 3);
        matcher.match(new MockSpringMvcResult(objectMapper.writeValueAsString(numbers)));
        // assert
    }
}
