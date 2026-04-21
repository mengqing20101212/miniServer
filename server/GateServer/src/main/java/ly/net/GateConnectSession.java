package ly.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Message;

import ly.GateClientManager;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;

/**
 * 缃戝叧杩炴帴浼氳瘽绫?
 * <p>
 * 璐熻矗澶勭悊缃戝叧鏈嶅姟鍣ㄤ笌瀹㈡埛绔箣闂寸殑杩炴帴锛岀鐞嗘秷鎭殑鎺ユ敹鍜岃浆鍙戯紝
 * 鏄綉鍏虫湇鍔″櫒涓殑鏍稿績缃戠粶浼氳瘽缁勪欢銆?
 */
public class GateConnectSession extends ConnectSession {
    /**
     * 鏋勯€犲嚱鏁?
     *
     * @param guid 浼氳瘽鍏ㄥ眬鍞竴鏍囪瘑绗?
     */
    public GateConnectSession(long guid) {
        super(guid);
    }

    /**
     * 浼氳瘽蹇冭烦鏇存柊鏂规硶
     * <p>
     * 缃戝叧浼氳瘽鏆備笉闇€瑕佺壒瀹氱殑蹇冭烦澶勭悊閫昏緫
     */
    @Override
    public void tick() {
        // 缃戝叧浼氳瘽蹇冭烦鏇存柊锛岀洰鍓嶆棤闇€鐗规畩澶勭悊
    }

    /**
     * 澶勭悊鎺ユ敹鍒扮殑鏁版嵁鍖?
     * <p>
     * 鏍规嵁鏁版嵁鍖呯被鍨嬭繘琛屼笉鍚岀殑澶勭悊锛?
     * 1. 瀹㈡埛绔埌鏈嶅姟鍣ㄧ殑鏁版嵁鍖?AbstractMessagePacket)
     * 2. 鏈嶅姟鍣ㄥ埌鏈嶅姟鍣ㄧ殑鏁版嵁鍖?AbstractMessagePacket)
     *
     * @param packet 鎺ユ敹鍒扮殑鏁版嵁鍖?
     */
    @Override
    public void addReceivePacket(AbstractMessagePacket packet) {
        // 璋冪敤鐖剁被鏂规硶杩涜鍩虹澶勭悊
        super.addReceivePacket(packet);

        // 璁板綍鎺ユ敹鍒扮殑鏁版嵁鍖呬俊鎭?
        LoggerDef.LogProto("receive {}|{}|{}|{}", getGuid(), packet.getSid(), packet.getCmd(), packet.getLength());

        // 缁熶竴鍖呯粨鏋勫悗锛岄€氳繃鍛戒护鍖洪棿鍒ゅ畾鏉ユ簮绫诲瀷锛?
        // 10000~20000 涓烘湇鍔″櫒闂存秷鎭紱鍏朵綑瑙嗕负瀹㈡埛绔姹傘€?
        boolean serverInnerCmd = packet.getCmd() > Cmd.CMD.CS_Server2Server_VALUE
                && packet.getCmd() <= Cmd.CMD.MaxServeMsgId_VALUE;

        // 澶勭悊瀹㈡埛绔埌鏈嶅姟鍣ㄧ殑鏁版嵁鍖?
        if (!serverInnerCmd && packet.getCmd() != Cmd.CMD.SC_Logout_VALUE) {
            AbstractMessagePacket csPacket = packet;
            // 灏濊瘯鑾峰彇瀵瑰簲鐨勫鎴风瀵硅薄
            GateClient client = GateClientManager.getInstance().getClient(getGuid());

            // 濡傛灉瀹㈡埛绔璞′笉瀛樺湪锛堟湭鐧诲綍鐘舵€侊級锛屽垯浜ょ粰澶勭悊鍣ㄨ矾鐢卞鐞嗭紙濡傜櫥褰曡姹傦級
            if (client == null) {
                try {
                    // 鎵ц澶勭悊鍣ㄨ矾鐢?
                    HandlerRouterManager.execute(this, csPacket);
                    // 澶勭悊瀹屾瘯鍚庡叧闂€氶亾
                    closeChannel();
                } catch (Exception e) {
                    // 璁板綍寮傚父淇℃伅
                    LoggerDef.SystemLogger.error("GateConnectSession addReceivePacket error, cmd={}", csPacket.getCmd(), e);
                    e.printStackTrace();
                }
            } else {
                // 宸茬櫥褰曠姸鎬侊紝杞彂鏁版嵁鍖呭埌娓告垙鏈嶅姟鍣?
                client.sendPacketToGameServer(csPacket);
            }
        }
        // 澶勭悊鏈嶅姟鍣ㄥ埌鏈嶅姟鍣ㄧ殑鏁版嵁鍖?
        else {
            AbstractMessagePacket s2sPacket = packet;
            // 澶勭悊鐧诲嚭鍛戒护鐗规畩鎯呭喌
            if (s2sPacket.getCmd() == Cmd.CMD.SC_Logout_VALUE) {
                HandlerRouterManager.execute(this, s2sPacket);
            } else {
                // 鏌ユ壘瀵瑰簲鐨勫鎴风骞惰浆鍙戞秷鎭?
                GateClient client = GateClientManager.getInstance().getClient(getGuid());
                if (client == null) {
                    // 鍏煎閮ㄥ垎閾捐矾閫氳繃 sid 鍏宠仈瀹㈡埛绔殑鎯呭喌
                    client = GateClientManager.getInstance().getClient((long) s2sPacket.getSid());
                }
                if (client != null) {
                    client.sendPacketToClient(s2sPacket);
                }
            }
        }
    }


    /**
     * 鍚戝鎴风鍙戦€佹秷鎭?
     *
     * @param cmd 鍛戒护ID
     * @param msg Protobuf娑堟伅瀵硅薄
     */
    public void sendClientMsg(int cmd, Message msg) {
        if (!(msg instanceof AbstractMessage)) {
            throw new IllegalArgumentException("msg must extend AbstractMessage");
        }
        // 鍒涘缓鏈嶅姟鍣ㄥ埌瀹㈡埛绔殑鏁版嵁鍖?
        AbstractMessagePacket s2cPacket = PacketCompat.createPacket(getGuid(), cmd, 0, 0, msg.toByteArray());
        // 娣诲姞鍒板彂閫侀槦鍒?
        addSendPacket(s2cPacket);
    }
}
