package com.github.json.enforcer;

import com.github.json.enforcer.internal.MockSpringMvcResult;
import com.github.json.enforcer.test_domain.BestPrice;
import com.github.json.enforcer.test_domain.Notebook;
import com.github.json.enforcer.test_domain.Shop;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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
                new HashSet<String>(Arrays.asList("ultrabook", "mobile")),
                new ArrayList<Shop>(Arrays.asList(new Shop("Bucharest"))), null);
        final String json = objectMapper.writeValueAsString(notebook);

        builder.arrays("tags", "shops");
        builder.arrayContentMatcher("shops", new JsonMatcherBuilder().fields("address").build());
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

        final JsonMatcher priceMatcher = CoreMatchers.fieldValue("price", 4.0);

        builder.objects("bestPrice");
        builder.objectMatcher("bestPrice", priceMatcher);
        // then
        JsonMatcher matcher = builder.build();
        // assert
        matcher.match(new MockSpringMvcResult(json));
    }


}
