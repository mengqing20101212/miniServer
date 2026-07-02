package ly.net;

import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 兼容不同 core 版本的包构建工具。
 */
public final class PacketCompat {
    private PacketCompat() {
    }

    public static AbstractMessagePacket createPacket(long guid, int cmd, int sid, int seq, byte[] data) {
        AbstractMessagePacket packet = createEmptyPacket();
        apply(packet, "setGuid", long.class, guid);
        apply(packet, "setCmd", int.class, cmd);
        apply(packet, "setSid", int.class, sid);
        apply(packet, "setSeq", int.class, seq);
        apply(packet, "setData", byte[].class, data);

        // setter 不存在时，尝试直接写字段
        applyField(packet, "guid", guid);
        applyField(packet, "cmd", cmd);
        applyField(packet, "sid", sid);
        applyField(packet, "seq", seq);
        applyField(packet, "data", data == null ? new byte[0] : data);
        return packet;
    }

    private static AbstractMessagePacket createEmptyPacket() {
        try {
            Method m = MessagePacketFactory.class.getMethod("createMessagePacket");
            return (AbstractMessagePacket) m.invoke(null);
        } catch (Exception ignored) {
            try {
                Method m = MessagePacketFactory.class.getMethod("createMessagePacket", int.class);
                return (AbstractMessagePacket) m.invoke(null, 0);
            } catch (Exception ignored2) {
                try {
                    Method m = MessagePacketFactory.class.getMethod("createAbstractMessagePacket", int.class, byte[].class);
                    return (AbstractMessagePacket) m.invoke(null, 0, new byte[0]);
                } catch (Exception ignored3) {
                    try {
                        Method m = MessagePacketFactory.class.getMethod("createAbstractMessagePacket", int.class, int.class, byte[].class);
                        return (AbstractMessagePacket) m.invoke(null, 0, 0, new byte[0]);
                    } catch (Exception e) {
                        throw new IllegalStateException("cannot create packet from MessagePacketFactory", e);
                    }
                }
            }
        }
    }

    private static void apply(AbstractMessagePacket packet, String name, Class<?> argType, Object value) {
        try {
            Method m = packet.getClass().getMethod(name, argType);
            m.invoke(packet, value);
        } catch (Exception ignored) {
        }
    }

    private static void applyField(AbstractMessagePacket packet, String fieldName, Object value) {
        try {
            Field field = findField(packet.getClass(), fieldName);
            if (field == null) {
                return;
            }
            field.setAccessible(true);
            field.set(packet, value);
        } catch (Exception ignored) {
        }
    }

    private static Field findField(Class<?> clz, String fieldName) {
        Class<?> cursor = clz;
        while (cursor != null) {
            try {
                return cursor.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                cursor = cursor.getSuperclass();
            }
        }
        return null;
    }
}
