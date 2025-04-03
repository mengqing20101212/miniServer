package ly.utils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/*
 * Author: liuYang
 * Date: 2025/4/3
 * File: RandomUtils
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
}
