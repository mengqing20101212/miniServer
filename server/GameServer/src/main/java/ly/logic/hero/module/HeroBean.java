package ly.logic.hero.module;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;

/**
 * 英雄数据 JavaBean
 */
@ProtobufClass
@EnableZigZap
public class HeroBean {
    @Protobuf(fieldType = FieldType.INT64, order = 1, required = false)
    public long heroUid;  // 英雄唯一ID（生成规则：playerId * 1000000 + heroId）

    @Protobuf(fieldType = FieldType.INT32, order = 2, required = false)
    public int heroId;    // 英雄配置ID

    @Protobuf(fieldType = FieldType.INT32, order = 3, required = false)
    public int level;     // 等级

    @Protobuf(fieldType = FieldType.INT32, order = 4, required = false)
    public int star;      // 星级

    @Protobuf(fieldType = FieldType.INT32, order = 5, required = false)
    public int awaken;    // 觉醒等级

    @Protobuf(fieldType = FieldType.INT64, order = 6, required = false)
    public long exp;      // 当前经验
}
