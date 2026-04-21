package ly.logic.player;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

import java.util.HashMap;
import java.util.Map;

@ProtobufClass
@EnableZigZap
public class PlayerModuleData {
    @Protobuf(fieldType = FieldType.MAP, order = 1, required = true)
    Map<String, byte[]> moduleData = new HashMap<>();

    public Map<String, byte[]> getModuleData() {
        return moduleData;
    }

    public void setModuleData(Map<String, byte[]> moduleData) {
        this.moduleData = moduleData;
    }

    public void addModuleData(String moduleName, byte[] data) {
        moduleData.put(moduleName, data);
    }

    public byte[] getModuleData(String moduleName) {
        return moduleData.get(moduleName);
    }

}
