package com.github.json.enforcer;

import com.github.json.enforcer.internal.InternalBundleReader;
import com.google.common.collect.ImmutableSet;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;

import java.util.Set;

/**
 * @author alex.dobjanschi
 * @since 9:55 PM 10/9/13
 */
public class FieldSimpleClassMatcher extends AbstractJsonMatcher {

    @SuppressWarnings("unchecked")
    private static final Set<Class> SIMPLE_FIELD_CLASSES = ImmutableSet.<Class>of(
            Integer.class, Long.class, Float.class, Double.class, String.class, Boolean.class);

    private String path;
    public FieldSimpleClassMatcher(String path) {
        this.path = path;
    }

    @Override
    protected void doMatch(String json) throws Exception {
        try {
            Object obj = JsonPath.read(json, path);
            if (!SIMPLE_FIELD_CLASSES.contains(obj.getClass())) {
                failWithMessage(path, obj.getClass());
            }
        } catch (InvalidPathException e) {
            failWithMessage(path);
        }
    }

    @Override
    public void failWithMessage(String path, Object... arguments) {
        throw new AssertionError(InternalBundleReader.getMessageAndFormat(
                "fieldNotSimpleClass", path, arguments[0]));
    }
}
