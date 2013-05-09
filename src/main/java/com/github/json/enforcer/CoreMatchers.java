package com.github.json.enforcer;

/**
 * @author alex.dobjanschi
 * @since 2:00 PM 5/9/13
 */
public class CoreMatchers {

    public static JsonMatcher arrayContents(Object ... objects) {
        return new ArrayContentsJsonMatcher(objects);
    }

    public static JsonMatcher arrayContents(int status, Object ... objects) {
        return new ArrayContentsJsonMatcher(status, objects);
    }

}
