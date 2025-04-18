// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Cmd.proto

// Protobuf Java Version: 3.25.6
package ly.proto;

public final class Cmd {
  private Cmd() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  /**
   * <pre>
   * 注意 双号是上行包，单号是下行包
   * </pre>
   *
   * Protobuf enum {@code CMD}
   */
  public enum CMD
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>CMD_null = 0;</code>
     */
    CMD_null(0),
    /**
     * <pre>
     *登录协议请求
     * </pre>
     *
     * <code>CS_Login = 100;</code>
     */
    CS_Login(100),
    /**
     * <pre>
     *登录协议响应
     * </pre>
     *
     * <code>SC_Login = 101;</code>
     */
    SC_Login(101),
    /**
     * <pre>
     *&#47;/////////////////////////////msgId 10000 -- 20000 之间的是server之间的消息号，客户端不占用///////////////////////////////////////
     * </pre>
     *
     * <code>CS_Server2Server = 10000;</code>
     */
    CS_Server2Server(10000),
    /**
     * <pre>
     *server2server 服务器之间通信的消息号 响应
     * </pre>
     *
     * <code>SC_Server2Server = 10001;</code>
     */
    SC_Server2Server(10001),
    /**
     * <pre>
     *maxServeMsgId 服务器之间通信消息号最大值
     * </pre>
     *
     * <code>MaxServeMsgId = 20000;</code>
     */
    MaxServeMsgId(20000),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>CMD_null = 0;</code>
     */
    public static final int CMD_null_VALUE = 0;
    /**
     * <pre>
     *登录协议请求
     * </pre>
     *
     * <code>CS_Login = 100;</code>
     */
    public static final int CS_Login_VALUE = 100;
    /**
     * <pre>
     *登录协议响应
     * </pre>
     *
     * <code>SC_Login = 101;</code>
     */
    public static final int SC_Login_VALUE = 101;
    /**
     * <pre>
     *&#47;/////////////////////////////msgId 10000 -- 20000 之间的是server之间的消息号，客户端不占用///////////////////////////////////////
     * </pre>
     *
     * <code>CS_Server2Server = 10000;</code>
     */
    public static final int CS_Server2Server_VALUE = 10000;
    /**
     * <pre>
     *server2server 服务器之间通信的消息号 响应
     * </pre>
     *
     * <code>SC_Server2Server = 10001;</code>
     */
    public static final int SC_Server2Server_VALUE = 10001;
    /**
     * <pre>
     *maxServeMsgId 服务器之间通信消息号最大值
     * </pre>
     *
     * <code>MaxServeMsgId = 20000;</code>
     */
    public static final int MaxServeMsgId_VALUE = 20000;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static CMD valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static CMD forNumber(int value) {
      switch (value) {
        case 0: return CMD_null;
        case 100: return CS_Login;
        case 101: return SC_Login;
        case 10000: return CS_Server2Server;
        case 10001: return SC_Server2Server;
        case 20000: return MaxServeMsgId;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<CMD>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        CMD> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<CMD>() {
            public CMD findValueByNumber(int number) {
              return CMD.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalStateException(
            "Can't get the descriptor of an unrecognized enum value.");
      }
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return ly.proto.Cmd.getDescriptor().getEnumTypes().get(0);
    }

    private static final CMD[] VALUES = values();

    public static CMD valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private CMD(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:CMD)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\tCmd.proto*r\n\003CMD\022\014\n\010CMD_null\020\000\022\014\n\010CS_L" +
      "ogin\020d\022\014\n\010SC_Login\020e\022\025\n\020CS_Server2Server" +
      "\020\220N\022\025\n\020SC_Server2Server\020\221N\022\023\n\rMaxServeMs" +
      "gId\020\240\234\001B\n\n\010ly.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
