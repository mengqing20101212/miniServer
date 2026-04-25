package ly.utils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * 公共工具类，提供时间、随机、位图、KV 解析或通用数据结构等辅助能力。
 */
public class RandomUtils {
    private static final String LETTERS_NUMBERS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CHINESE_START = 0x4E00;
    private static final int CHINESE_END = 0x9FFF;

    public static String generateRandomString(int length) {
        if (length <= 0) {
            return "";
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (random.nextBoolean()) { // 50%概率选择汉字
                int codePoint = CHINESE_START + random.nextInt(CHINESE_END - CHINESE_START + 1);
                sb.append((char) codePoint);
            } else { // 50%概率选择字母或数字
                sb.append(LETTERS_NUMBERS.charAt(random.nextInt(LETTERS_NUMBERS.length())));
            }
        }

        return new String(sb.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    public static int RandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static int RandomInt(int max) {
        return RandomInt(0, max);
    }

    public static long RandomLong(long max) {
        return (long) RandomInt((int) max);
    }
}
