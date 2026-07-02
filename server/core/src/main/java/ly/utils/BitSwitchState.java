package ly.utils;

import java.util.Arrays;

/**
 * 紧凑的位开关集合。
 * <p>
 * 根据 bitLength 自动选择 byte、short、int、long 或 long[] 作为底层存储，主要用于
 * Entry 脏字段跟踪这类“字段数量固定、只关心开关状态”的场景。
 */
public abstract class BitSwitchState {
    private final int bitLength;

    protected BitSwitchState(int bitLength) {
        if (bitLength <= 0) {
            throw new IllegalArgumentException("bitLength must be greater than 0");
        }
        this.bitLength = bitLength;
    }

    /** 按位数选择占用内存最小的实现。 */
    public static BitSwitchState of(int bitLength) {
        if (bitLength <= Byte.SIZE) {
            return new ByteBitSwitchState(bitLength);
        }
        if (bitLength <= Short.SIZE) {
            return new ShortBitSwitchState(bitLength);
        }
        if (bitLength <= Integer.SIZE) {
            return new IntBitSwitchState(bitLength);
        }
        if (bitLength <= Long.SIZE) {
            return new LongBitSwitchState(bitLength);
        }
        return new LongArrayBitSwitchState(bitLength);
    }

    public final int getBitLength() {
        return bitLength;
    }

    public final void enable(int bitIndex) {
        setEnabled(bitIndex, true);
    }

    public final void disable(int bitIndex) {
        setEnabled(bitIndex, false);
    }

    /** 统一检查 bit 下标，所有实现读写前都应调用。 */
    protected final void checkBitIndex(int bitIndex) {
        if (bitIndex < 0 || bitIndex >= bitLength) {
            throw new IndexOutOfBoundsException(
                    String.format("bitIndex out of range: %d, bitLength=%d", bitIndex, bitLength));
        }
    }

    public abstract boolean isEnabled(int bitIndex);

    public abstract void setEnabled(int bitIndex, boolean enabled);

    public abstract void clear();

    /** 返回底层原始存储值，主要用于调试或序列化。 */
    public abstract Object getRawValue();

    private static final class ByteBitSwitchState extends BitSwitchState {
        private byte value;

        private ByteBitSwitchState(int bitLength) {
            super(bitLength);
        }

        @Override
        public boolean isEnabled(int bitIndex) {
            checkBitIndex(bitIndex);
            return (value & (1 << bitIndex)) != 0;
        }

        @Override
        public void setEnabled(int bitIndex, boolean enabled) {
            checkBitIndex(bitIndex);
            value = (byte) BitUtils.updateBit(value & 0xFFL, bitIndex, enabled);
        }

        @Override
        public void clear() {
            value = 0;
        }

        @Override
        public Byte getRawValue() {
            return value;
        }
    }

    private static final class ShortBitSwitchState extends BitSwitchState {
        private short value;

        private ShortBitSwitchState(int bitLength) {
            super(bitLength);
        }

        @Override
        public boolean isEnabled(int bitIndex) {
            checkBitIndex(bitIndex);
            return (value & (1 << bitIndex)) != 0;
        }

        @Override
        public void setEnabled(int bitIndex, boolean enabled) {
            checkBitIndex(bitIndex);
            value = (short) BitUtils.updateBit(value & 0xFFFFL, bitIndex, enabled);
        }

        @Override
        public void clear() {
            value = 0;
        }

        @Override
        public Short getRawValue() {
            return value;
        }
    }

    private static final class IntBitSwitchState extends BitSwitchState {
        private int value;

        private IntBitSwitchState(int bitLength) {
            super(bitLength);
        }

        @Override
        public boolean isEnabled(int bitIndex) {
            checkBitIndex(bitIndex);
            return (value & (1 << bitIndex)) != 0;
        }

        @Override
        public void setEnabled(int bitIndex, boolean enabled) {
            checkBitIndex(bitIndex);
            value = (int) BitUtils.updateBit(value & 0xFFFFFFFFL, bitIndex, enabled);
        }

        @Override
        public void clear() {
            value = 0;
        }

        @Override
        public Integer getRawValue() {
            return value;
        }
    }

    private static final class LongBitSwitchState extends BitSwitchState {
        private long value;

        private LongBitSwitchState(int bitLength) {
            super(bitLength);
        }

        @Override
        public boolean isEnabled(int bitIndex) {
            checkBitIndex(bitIndex);
            return BitUtils.isBitSet(value, bitIndex);
        }

        @Override
        public void setEnabled(int bitIndex, boolean enabled) {
            checkBitIndex(bitIndex);
            value = BitUtils.updateBit(value, bitIndex, enabled);
        }

        @Override
        public void clear() {
            value = 0L;
        }

        @Override
        public Long getRawValue() {
            return value;
        }
    }

    private static final class LongArrayBitSwitchState extends BitSwitchState {
        private final long[] value;

        private LongArrayBitSwitchState(int bitLength) {
            super(bitLength);
            this.value = new long[BitUtils.requiredWordCount(bitLength)];
        }

        @Override
        public boolean isEnabled(int bitIndex) {
            checkBitIndex(bitIndex);
            int wordIndex = BitUtils.wordIndex(bitIndex);
            int bitOffset = BitUtils.bitOffset(bitIndex);
            return BitUtils.isBitSet(value[wordIndex], bitOffset);
        }

        @Override
        public void setEnabled(int bitIndex, boolean enabled) {
            checkBitIndex(bitIndex);
            int wordIndex = BitUtils.wordIndex(bitIndex);
            int bitOffset = BitUtils.bitOffset(bitIndex);
            value[wordIndex] = BitUtils.updateBit(value[wordIndex], bitOffset, enabled);
        }

        @Override
        public void clear() {
            Arrays.fill(value, 0L);
        }

        @Override
        public long[] getRawValue() {
            return value.clone();
        }
    }
}
