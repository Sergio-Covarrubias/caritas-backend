package com.caritas.backend.common;

import java.util.Arrays;

public class TextUtils {
    public static String[] StringToArray(String input) {
        return !input.isEmpty() ? Arrays.stream(input.split(",")).map(String::trim).toArray(String[]::new) : new String[0];
    }

    public static String ArrayToString(String[] input) {
        return input.length > 0 ? String.join(",", input) : "";
    }
}
