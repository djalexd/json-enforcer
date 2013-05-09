package com.github.json.enforcer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Setups the objectMapper
 *
 * @author alex.dobjanschi
 * @since 1:36 PM 5/9/13
 */
public abstract class BaseJsonMatcherTest {

    protected static ObjectMapper objectMapper;

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

}
