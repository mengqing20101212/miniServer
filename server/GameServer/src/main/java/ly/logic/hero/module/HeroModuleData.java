package ly.logic.hero.module;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import java.util.ArrayList;
import java.util.List;

/**
 * 英雄模块数据
 */
@ProtobufClass
@EnableZigZap
public class HeroModuleData {
    @Protobuf(fieldType = FieldType.OBJECT, order = 1, required = false)
    public List<HeroEntry> heroList = new ArrayList<>();

    @Protobuf(fieldType = FieldType.INT32, order = 2, required = false)
    public int maxHeroCount = 100; // 最大英雄数量
}
