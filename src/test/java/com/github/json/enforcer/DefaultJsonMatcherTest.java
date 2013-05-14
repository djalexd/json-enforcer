package com.github.json.enforcer;

import com.github.json.enforcer.internal.MockSpringMvcResult;
import com.github.json.enforcer.test_domain.BestPrice;
import com.github.json.enforcer.test_domain.Notebook;
import com.github.json.enforcer.test_domain.Shop;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.*;

/**
 * @author alex.dobjanschi
 * @since 1:48 PM 5/8/13
 */
public class DefaultJsonMatcherTest extends BaseJsonMatcherTest {
    JsonMatcherBuilder builder;

    @Before
    public void setupJsonMatcherBuilder() {
        builder = new JsonMatcherBuilder();
    }

    @After
    public void tearDownJsonMatcherBuilder() {
        builder = null;
    }


    @Test
    public void should_validate_simple_fields() throws Exception {
        // when
        Notebook notebook = new Notebook(3, "XPS", null, null, null);
        final String json = objectMapper.writeValueAsString(notebook);

        builder.fields("identifier", "name");
        // then
        JsonMatcher matcher = builder.build();
        // assert
        matcher.match(new MockSpringMvcResult(json));
    }


    @Test
    public void should_validate_array_fields() throws Exception {
        // when
        Notebook notebook = new Notebook(null, null,
                Sets.newHashSet("ultrabook", "mobile"),
                Lists.newArrayList(new Shop("Bucharest")), null);
        final String json = objectMapper.writeValueAsString(notebook);

        builder.arrays("tags", "shops");
        // then
        JsonMatcher matcher = builder.build();
        // assert
        matcher.match(new MockSpringMvcResult(json));
    }


    @Test
    public void should_validate_object() throws Exception {
        // when
        Notebook notebook = new Notebook(null, null, null, null, new BestPrice(4.0));
        final String json = objectMapper.writeValueAsString(notebook);

        builder.objects("bestPrice");
        // then
        JsonMatcher matcher = builder.build();
        // assert
        matcher.match(new MockSpringMvcResult(json));
    }


}
