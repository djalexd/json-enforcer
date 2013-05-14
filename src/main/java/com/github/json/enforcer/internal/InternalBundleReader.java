package com.github.json.enforcer.internal;

import java.util.ResourceBundle;

/**
 * @author alex.dobjanschi
 * @since 10:46 AM 5/14/13
 */
public final class InternalBundleReader {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("enforcer-errors");

    /**
     * Reads the given key from bundle and formats the String
     * with given arguments.
     *
     * @param key
     * @param arguments
     * @return
     */
    public static String getMessageAndFormat(String key, Object ... arguments) {
        final String rawMessage = bundle.getString(key);
        if (rawMessage == null) {
            throw new IllegalArgumentException("No String found for key '" + key + "'");
        }
        return String.format(rawMessage, arguments);
    }
}
