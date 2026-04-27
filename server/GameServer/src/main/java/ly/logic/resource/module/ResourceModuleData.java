package ly.logic.resource.module;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import java.util.HashMap;
import java.util.Map;

/**
 * 资源模块数据
 */
@ProtobufClass
@EnableZigZap
public class ResourceModuleData {
    @Protobuf(fieldType = FieldType.MAP, order = 1, required = false)
    public Map<Integer, Long> resources = new HashMap<>();
}
