package com.alibaba.csp.sentinel.dashboard.support;

import org.apache.commons.codec.digest.DigestUtils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;
/**
 * 密码加密
 *
 * @author lime
 */
public class ImsPasswordEncoder {
    /**
     * 盐值密码分割符
     */
    private final String DELIMITER = "@";
    /**
     * 盐值长度
     */
    private final int SALT_SIZE = 18;
    /**
     * 加密次数
     */
    private static int ITERATIONS_SIZE = 16;
    public String encode(CharSequence rawPassword) {
        String salt = Hex.getRandomSalt(SALT_SIZE);
        return salt + DELIMITER + hash(rawPassword.toString(), salt);
    }
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        final int defaultSplitSize = 2;
        String[] split = encodedPassword.split(DELIMITER);
        if (split.length != defaultSplitSize) {
            return false;
        }
        return hash(rawPassword.toString(), split[0]).equals(split[1]);
    }
    private static String hash(String rawPassword, String salt) {
        return Hex.encodeToString(hash(rawPassword.getBytes(), salt.getBytes(), ITERATIONS_SIZE));
    }
    private static byte[] hash(byte[] bytes, byte[] salt, int hashIterations) {
        MessageDigest digest = DigestUtils.getDigest("SHA-256");
        if (salt != null) {
            digest.reset();
            digest.update(salt);
        }
        byte[] hashed = digest.digest(bytes);
        int iterations = hashIterations - 1;
        for (int i = 0; i < iterations; ++i) {
            digest.reset();
            hashed = digest.digest(hashed);
        }
        return hashed;
    }
    protected static class Hex {
        private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        static final String B64T = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        public Hex() {
        }
        public static String encodeToString(byte[] bytes) {
            char[] encodedChars = encode(bytes);
            return new String(encodedChars);
        }
        public static char[] encode(byte[] data) {
            int l = data.length;
            char[] out = new char[l << 1];
            int i = 0;
            for (int var4 = 0; i < l; ++i) {
                out[var4++] = DIGITS[(240 & data[i]) >>> 4];
                out[var4++] = DIGITS[15 & data[i]];
            }
            return out;
        }
        public static byte[] decode(byte[] array) throws IllegalArgumentException {
            String s = null;
            try {
                s = new String(array, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException(e);
            }
            return decode(s);
        }
        public static byte[] decode(String hex) {
            return decode(hex.toCharArray());
        }
        public static byte[] decode(char[] data) throws IllegalArgumentException {
            int len = data.length;
            if ((len & 1) != 0) {
                throw new IllegalArgumentException("Odd number of characters.");
            } else {
                byte[] out = new byte[len >> 1];
                int i = 0;
                for (int j = 0; j < len; ++i) {
                    int f = toDigit(data[j], j) << 4;
                    ++j;
                    f |= toDigit(data[j], j);
                    ++j;
                    out[i] = (byte) (f & 255);
                }
                return out;
            }
        }
        protected static int toDigit(char ch, int index) throws IllegalArgumentException {
            int digit = Character.digit(ch, 16);
            if (digit == -1) {
                throw new IllegalArgumentException("Illegal hexadecimal character " + ch + " at index " + index);
            } else {
                return digit;
            }
        }
        protected static String getRandomSalt(final int num) {
            final StringBuilder saltString = new StringBuilder();
            for (int i = 1; i <= num; i++) {
                saltString.append(B64T.charAt(new Random().nextInt(B64T.length())));
            }
            return saltString.toString();
        }
    }
}