package com.beautyboss.slogen.config.service.utils;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Author : Slogen
 * Date   : 2019/2/13
 */
public class SecurityUtils {

    public static Long getCurrentUserId() {
        /**
         * TODO
         */
        return 10000L;
    }

    public static String tryDecode(String value) {
        if (value != null && value.startsWith("~{") && value.endsWith("}")) {
            try {
                return decode(value.substring(2, value.length() - 1));
            } catch (Exception e) {
            }
        }
        return value;
    }

    private static String decode(String src) {
        int len = src.length();
        ByteBuffer bb = ByteBuffer.allocate((len - 3) / 2);
        int p = Character.digit(src.charAt(0), 16) & 0x07;
        int q = Character.digit(src.charAt(1), 16);
        int k = Character.digit(src.charAt(2), 16);

        for (int i = 3; i < len; i += 2) {
            byte high = (byte) (Character.digit(src.charAt(i), 16) & 0xFF);
            byte low = (byte) (Character.digit(src.charAt(i + 1), 16) & 0xFF);

            bb.put((byte) (high << 4 | low));
        }

        byte[] data = (byte[]) bb.flip().array();

        mask(data, k);
        swap(data, p, q);

        try {
            return new String(data, 0, data.length - 13, "utf-8");
        } catch (IOException e) {
            return new String(data, 0, data.length - 13);
        }
    }

    private static void mask(byte[] data, int k) {
        for (int i = data.length - 1; i >= 0; i--) {
            data[i] ^= k;
        }
    }

    private static void swap(byte[] data, int p, int q) {
        int len = data.length * 8;

        for (int i = 0; i < len; i += p) {
            int j = i + q;

            if (j < len) {
                byte b1 = data[i / 8];
                byte b2 = data[j / 8];
                int f1 = b1 & (1 << (i % 8));
                int f2 = b2 & (1 << (j % 8));

                if ((f1 != 0) != (f2 != 0)) {
                    data[i / 8] ^= 1 << (i % 8);
                    data[j / 8] ^= 1 << (j % 8);
                }
            }
        }
    }

}
