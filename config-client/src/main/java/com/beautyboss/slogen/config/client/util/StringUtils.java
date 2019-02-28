package com.beautyboss.slogen.config.client.util;

import java.util.Random;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
public class StringUtils {
    private static final String DEFAULT_REPLACEMENT = "...";
    private static final int DEFAULT_HALF_COUNT = 10;

    public static String cutString(String input) {
        return cutString(input, DEFAULT_HALF_COUNT);
    }

    public static String cutString(String input, int halfCount) {
        return cutString(input, halfCount, DEFAULT_REPLACEMENT);
    }

    public static String cutString(String input, int halfCount, String replace) {
        if (isEmpty(input)) {
            return "";
        } else {
            if (input.length() > 2 * halfCount) {
                return input.substring(0, halfCount) + replace
                        + input.substring(input.length() - halfCount);
            } else {
                return input;
            }
        }
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    public static boolean isNotEmpty(String str) {
        return (str != null && str.length() > 0);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    private static Random rand = new Random();
    public static void shuffle(String[] array) {
        for (int i=array.length; i>1; i--)
            swap(array, i-1, rand.nextInt(i));
    }

    public static void swap(Object[] arr, int i, int j) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
