package ly;

/** 生成表的构造工厂，不持有任何正在使用的配置实例。 */
public interface InterfaceConfigManagerProxy {
    AbstractConfigManger createTable();

    String getConfigFileName();
}
