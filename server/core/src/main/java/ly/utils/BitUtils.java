package ly.utils;

/**
 * 公共工具类，提供时间、随机、位图、KV 解析或通用数据结构等辅助能力。
 */
public final class BitUtils {
    private BitUtils() {
    }

    public static boolean isBitSet(long value, int bitIndex) {
        checkBitIndex(bitIndex, Long.SIZE);
        return (value & (1L << bitIndex)) != 0;
    }

    public static long setBit(long value, int bitIndex) {
        checkBitIndex(bitIndex, Long.SIZE);
        return value | (1L << bitIndex);
    }

    public static long clearBit(long value, int bitIndex) {
        checkBitIndex(bitIndex, Long.SIZE);
        return value & ~(1L << bitIndex);
    }

    public static long updateBit(long value, int bitIndex, boolean enabled) {
        return enabled ? setBit(value, bitIndex) : clearBit(value, bitIndex);
    }

    public static int requiredWordCount(int bitLength) {
        if (bitLength <= 0) {
            throw new IllegalArgumentException("bitLength must be greater than 0");
        }
        return (bitLength + Long.SIZE - 1) / Long.SIZE;
    }

    public static int wordIndex(int bitIndex) {
        if (bitIndex < 0) {
            throw new IllegalArgumentException("bitIndex must be greater than or equal to 0");
        }
        return bitIndex / Long.SIZE;
    }

    public static int bitOffset(int bitIndex) {
        if (bitIndex < 0) {
            throw new IllegalArgumentException("bitIndex must be greater than or equal to 0");
        }
        return bitIndex % Long.SIZE;
    }

    private static void checkBitIndex(int bitIndex, int bitLength) {
        if (bitIndex < 0 || bitIndex >= bitLength) {
            throw new IndexOutOfBoundsException(
                    String.format("bitIndex out of range: %d, bitLength=%d", bitIndex, bitLength));
        }
    }
}
