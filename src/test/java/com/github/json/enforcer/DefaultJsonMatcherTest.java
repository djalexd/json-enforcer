package com.github.json.enforcer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.json.enforcer.support.MockSpringMvcResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.*;

import java.util.List;
import java.util.Set;

/**
 * @author alex.dobjanschi
 * @since 1:48 PM 5/8/13
 */
public class DefaultJsonMatcherTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public static void buildObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @AfterClass
    public static void tearDownObjectMapper() {
        objectMapper = null;
    }

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





    /**
     * Internal class used for testing.
     */
    @SuppressWarnings("unused")
    public static class Notebook {

        Integer identifier;
        String name;
        Set<String> tags;
        List<Shop> shops;
        BestPrice bestPrice;


        Notebook(Integer identifier, String name, Set<String> tags, List<Shop> shops, BestPrice bestPrice) {
            this.identifier = identifier;
            this.name = name;
            this.tags = tags;
            this.shops = shops;
            this.bestPrice = bestPrice;
        }

        public BestPrice getBestPrice() {
            return bestPrice;
        }

        public Integer getIdentifier() {
            return identifier;
        }

        public String getName() {
            return name;
        }

        public Set<String> getTags() {
            return tags;
        }

        public List<Shop> getShops() {
            return shops;
        }
    }


    /**
     * Internal class used for testing.
     */
    @SuppressWarnings("unused")
    public static class Shop {

        String address;
        int openHours;
        int closeHours;

        Shop(String address) {
            this(address, 8, 20);
        }

        Shop(String address, int openHours, int closeHours) {
            this.address = address;
            this.openHours = openHours;
            this.closeHours = closeHours;
        }

        public String getAddress() {
            return address;
        }

        public int getOpenHours() {
            return openHours;
        }

        public int getCloseHours() {
            return closeHours;
        }
    }

    /**
     * Internal class used for testing.
     */
    @SuppressWarnings("unused")
    static class BestPrice {
        double price;

        BestPrice(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }


}
