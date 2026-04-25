package ly;

/**
 * ConfigLoadException 的核心定义，承载所在包对应的业务模型或辅助逻辑。
 */
public class ConfigLoadException extends RuntimeException {
    public ConfigLoadException(String message) {
        super(message);
    }
}
