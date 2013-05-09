package com.github.json.enforcer;

import com.github.json.enforcer.support.MockSpringMvcResult;
import com.github.json.enforcer.test_domain.Notebook;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

/**
 * @author alex.dobjanschi
 * @since 1:36 PM 5/9/13
 */
public class ArrayContentsJsonMatcherTest extends BaseJsonMatcherTest {

    JsonMatcher jsonMatcher;


    @Test
    public void should_validate_array_contents() throws Exception {
        // when
        String tagsAsString = objectMapper.writeValueAsString(
                ImmutableSet.of("tag1", "tag2", "unknown"));
        jsonMatcher = new ArrayContentsJsonMatcher(-1, "tag1", "tag2", "unknown");
        // then
        jsonMatcher.match(new MockSpringMvcResult(tagsAsString));
        // assert -- no need, there was no exception thrown.
    }

    @Test
    public void should_validate_array_contents_unordered() throws Exception {
        // when
        String tagsAsString = objectMapper.writeValueAsString(
                ImmutableSet.of("tag1", "tag2", "unknown"));
        jsonMatcher = new ArrayContentsJsonMatcher(-1, "unknown", "tag1", "tag2");
        // then
        jsonMatcher.match(new MockSpringMvcResult(tagsAsString));
        // assert -- no need, there was no exception thrown.
    }


    @Test(expected = AssertionError.class)
    public void should_throw_assertionError_if_object_not_found_in_array_contents() throws Exception {
        // when
        String tagsAsString = objectMapper.writeValueAsString(
                ImmutableSet.of("tag2"));

        jsonMatcher = new ArrayContentsJsonMatcher(-1, "tag1", "tag2", "unknown");
        // then
        jsonMatcher.match(new MockSpringMvcResult(tagsAsString));
        // assert - built-in.
    }

    @Test(expected = AssertionError.class)
    public void should_throw_assertionError_if_object_not_found_in_array_contents_different_class() throws Exception {
        // when
        String tagsAsString = objectMapper.writeValueAsString(
                ImmutableSet.of("tag2"));

        jsonMatcher = new ArrayContentsJsonMatcher(-1, "tag1", 2, 5.0);
        // then
        jsonMatcher.match(new MockSpringMvcResult(tagsAsString));
        // assert - built-in.
    }

    @Test
    public void should_validate_for_subpath() throws Exception {
        // when
        Notebook notebook = new Notebook(null, null, ImmutableSet.of("tag1", "tag2"), null, null);
        String notebookAsString = objectMapper.writeValueAsString(notebook);

        jsonMatcher = new JsonMatcherBuilder()
                .arrays("tags")
                .arrayMatcher("tags", new ArrayContentsJsonMatcher(-1, "tag1", "tag2"))
                .build();

        // then
        jsonMatcher.match(new MockSpringMvcResult(notebookAsString));
        // assert - built-in.
    }

}