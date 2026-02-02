# MiniServer - 分布式游戏服务器框架

基于 Nacos 实现的高性能、可扩展的分布式游戏服务器框架，专为卡牌、肉鸽(Roguelike)和棋牌类游戏设计。

## 🚀 特性

- **微服务架构**: 基于 Nacos 实现服务注册与发现，支持动态扩缩容
- **高性能网络**: 使用 Netty 作为底层网络框架，支持高并发连接
- **虚拟线程**: 利用 Java 19+ 虚拟线程提升并发性能，降低资源消耗
- **灵活配置**: Excel 配置表自动转换为 Java 配置类，支持热更新
- **协议支持**: 基于 Protobuf 的高效数据序列化，支持多种消息格式
- **多服务器类型**: 支持登录、网关、游戏等多种服务器角色，职责分离
- **分布式锁**: 基于 Redis 的分布式锁机制，保证数据一致性
- **RPC通信**: 服务器间远程过程调用支持，实现服务间通信
- **序列号校验**: 实现消息序列号校验机制，防止网络丢包
- **异步处理**: 消息异步队列机制，提升处理效率

## 🏗️ 系统架构

![系统架构图](server/doc/serverStage.png)

### 服务器类型详解

- **LoginServer**: 基于 Spring Boot 的登录认证服务器，负责用户认证和会话管理
  - 用户登录验证
  - Token生成与验证
  - 会话管理
  - 服务器分配策略

- **GateServer**: 网关服务器，处理客户端连接和消息转发
  - 客户端连接管理
  - 消息路由与转发
  - 连接状态监控
  - 流量控制与限流

- **GameServer**: 游戏逻辑服务器，处理具体业务逻辑
  - 游戏业务逻辑处理
  - 玩家数据管理
  - 游戏房间管理
  - 实时对战逻辑

- **CenterServer**: 中心服务器，处理全局逻辑
  - 全局排行榜
  - 活动管理
  - 全服公告
  - 跨服交互

- **ChatServer**: 聊天服务器，专门处理聊天功能
  - 公聊私聊
  - 聊天过滤
  - 消息持久化
  - 聊天频道管理

### 核心组件详解

#### 网络层
- `NetService`: 网络服务管理器，管理Netty服务器和连接会话
- `ConnectSession`: 连接会话管理，封装客户端连接状态和消息队列
- `HandlerRouterManager`: 消息处理器路由管理，根据CMD路由到对应处理器
- `NetServer`: Netty服务器封装，处理网络IO事件
- `ClientHandler`: 客户端消息处理器，处理连接建立、断开和消息收发

#### 配置层
- `ConfigService`: 配置服务管理器，负责加载所有配置
- `ParserExcelConfig`: Excel配置表解析器，将Excel转换为文本配置
- `ServerConfig`: 服务器配置类，包含服务器IP、端口、数据库等配置
- `AbstractConfigManger`: 抽象配置管理器，提供通用配置加载逻辑
- `InterfaceConfigManagerProxy`: 配置管理代理接口，支持热更新

#### RPC层
- `RpcService`: RPC服务管理，维护服务器间连接池
- `RpcNodeConnector`: 服务器节点连接器，管理与其他服务器的连接
- `RpcUtils`: RPC工具类，提供同步和异步调用方法
- `NacosService`: Nacos服务发现与配置管理，实现服务注册与发现

#### 数据存储
- `MysqlService`: MySQL数据库服务，提供数据库连接和操作
- `RedisUtils`: Redis缓存工具，提供常用缓存操作
- `RedisKeys`: Redis键值管理，定义所有Redis键的命名规范
- `MysqlConnector`: MySQL连接器，管理数据库连接池

#### 核心框架
- `ServerContext`: 服务器上下文，管理服务器全局状态
- `ConnectSession`: 连接会话基类，管理客户端连接状态
- `Connector`: 连接器，封装底层网络连接
- `LoggerDef`: 日志定义，统一日志输出格式

## 📁 项目结构详解

```
miniServer/
├── excel/                 # Excel配置表
│   ├── serverConfig/      # 生成的配置文本文件
│   └── *.xlsx             # 原始Excel配置表（策划表）
├── logs/                  # 日志文件
│   ├── net.log            # 网络层日志
│   └── system.log         # 系统运行日志
├── proto/                 # Protocol Buffers定义
│   ├── bin/               # protoc编译器
│   ├── Cmd.proto          # 命令枚举定义
│   ├── Login.proto        # 登录协议定义
│   ├── ErrorMsg.proto     # 错误消息定义
│   ├── Server.proto       # 服务器间通信协议
│   └── *.proto            # 其他协议定义
├── server/                # 服务器源码根目录
│   ├── config/            # 配置管理模块
│   │   ├── src/main/java/ly/config/
│   │   │   ├── *.java     # 各种配置类（自动生成）
│   │   │   ├── ServerConfig.java    # 服务器配置
│   │   │   └── DbConfig.java        # 数据库配置
│   │   └── pom.xml        # Maven配置
│   ├── core/              # 核心框架模块
│   │   ├── src/main/java/ly/          # 核心类
│   │   │   ├── ServerContext.java     # 服务器上下文
│   │   │   ├── IServer.java           # 服务器接口
│   │   │   ├── Main.java              # 启动入口
│   │   │   ├── LoggerDef.java         # 日志定义
│   │   │   └── utils/                 # 工具类
│   │   ├── src/main/java/ly/cache/    # 缓存服务
│   │   ├── src/main/java/ly/config/   # 配置管理
│   │   ├── src/main/java/ly/db/       # 数据库访问
│   │   ├── src/main/java/ly/game/     # 游戏逻辑
│   │   ├── src/main/java/ly/net/      # 网络层
│   │   ├── src/main/java/ly/nacos/    # Nacos服务
│   │   ├── src/main/java/ly/redis/    # Redis服务
│   │   ├── src/main/java/ly/rpc/      # RPC服务
│   │   ├── src/main/resources/        # 资源文件
│   │   └── pom.xml        # Maven配置
│   ├── GameServer/        # 游戏服务器
│   │   ├── src/main/java/ly/          # 游戏服务器主类
│   │   ├── src/main/java/ly/logic/    # 游戏业务逻辑
│   │   ├── src/main/java/ly/net/      # 游戏服务器网络层
│   │   └── pom.xml        # Maven配置
│   ├── GateServer/        # 网关服务器
│   │   ├── src/main/java/ly/          # 网关服务器主类
│   │   ├── src/main/java/ly/logic/    # 网关业务逻辑
│   │   ├── src/main/java/ly/net/      # 网关网络层
│   │   └── pom.xml        # Maven配置
│   ├── LoginServer/       # 登录服务器
│   │   ├── src/main/java/ly/loginserver/ # 登录服务器主类
│   │   ├── src/main/resources/        # Spring Boot配置
│   │   └── pom.xml        # Maven配置
│   ├── proto/             # 协议处理模块
│   │   ├── src/main/java/ly/proto/    # 生成的协议类
│   │   └── pom.xml        # Maven配置
│   ├── tool/              # 工具模块
│   │   ├── src/main/java/ly/          # 各种工具类
│   │   │   ├── ParserExcelConfig.java # Excel解析器
│   │   │   ├── ParserProto.java       # 协议解析器
│   │   │   └── ToolMain.java          # 工具主入口
│   │   └── pom.xml        # Maven配置
│   └── doc/               # 文档目录
│       ├── serverStage.png # 系统架构图
│       └── serverStage.puml # PlantUML源码
├── README.md              # 项目说明文档
└── .gitignore             # Git忽略文件配置
```

## 🛠️ 快速开始

### 环境要求

- **Java**: 19+ (推荐使用最新LTS版本)
- **Maven**: 3.6.0+
- **Nacos**: 2.x (推荐2.2.0+)
- **MySQL**: 5.7+ 或 8.0+
- **Redis**: 6.0+
- **操作系统**: Linux/macOS/Windows

### 启动准备

1. **安装依赖服务**
   ```bash
   # 安装并启动 Nacos
   docker run --name nacos-standalone -e MODE=standalone -p 8848:8848 -d nacos/nacos-server:latest
   
   # 安装并启动 MySQL
   docker run --name mysql-game -e MYSQL_ROOT_PASSWORD=root123 -e MYSQL_DATABASE=gamedb -p 3306:3306 -d mysql:8.0
   
   # 安装并启动 Redis
   docker run --name redis-game -p 6379:6379 -d redis:latest
   ```

2. **克隆项目**
   ```bash
   git clone <repository-url>
   cd miniServer
   ```

3. **编译项目**
   ```bash
   cd server
   mvn clean install -DskipTests
   ```

### 启动各服务器模块

#### 启动配置服务器 (Config Server)
```bash
cd config
mvn compile exec:java -Dexec.mainClass="ly.Main"
```

#### 启动网关服务器 (Gate Server)
```bash
cd ../GateServer
java -cp target/gateserver-1.0-SNAPSHOT.jar ly.GateServer localhost:8848 ly gate1001
```

#### 启动游戏服务器 (Game Server)
```bash
cd ../GameServer
java -cp target/gameserver-1.0-SNAPSHOT.jar ly.GameServer localhost:8848 ly game1001
```

#### 启动登录服务器 (Login Server)
```bash
cd ../LoginServer
./mvnw spring-boot:run
```

### 配置表管理

本框架支持将 Excel 配置表自动转换为 Java 配置类：

1. **Excel格式要求**
   - 第1行：服务器字段名
   - 第2行：客户端字段名
   - 第3行：数据类型（INT, STRING, DOUBLE等）
   - 第4行：字段注释
   - 第5行：预留行
   - 第6行开始：数据内容，首列标记为"#"表示有效数据

2. **转换配置表**
   ```bash
   cd server/tool
   mvn compile exec:java -Dexec.mainClass="ly.ToolMain" -Dexec.args="parserExcelConfig ../../excel"
   ```

3. **自定义配置类**
   生成的配置类中包含自定义区域标记：
   ```java
   // @@@@@自定义属性开始区@@@@@
   // 在此处添加自定义属性
   // @@@@@自定义属性结束区@@@@@
   
   // @@@@@自定义方法开始区@@@@@
   // 在此处添加自定义方法
   // @@@@@自定义方法结束区@@@@@
   ```

### Nacos配置

在Nacos中需要配置以下内容：

1. **网关服务器配置** (Data ID: gate1001, Group: GATE)
   ```yaml
   configPath: "/path/to/config"
   db:
     jdbcUrl: "jdbc:mysql://localhost:3306/gamedb"
     userName: "root"
     passWord: "root123"
   redis:
     host: "localhost"
     port: 6379
   serverPort: 9001
   serverIp: "127.0.0.1"
   runModule: "production"
   ```

2. **游戏服务器配置** (Data ID: game1001, Group: GAME)
   ```yaml
   configPath: "/path/to/config"
   db:
     jdbcUrl: "jdbc:mysql://localhost:3306/gamedb"
     userName: "root"
     passWord: "root123"
   redis:
     host: "localhost"
     port: 6379
   serverPort: 9002
   serverIp: "127.0.0.1"
   runModule: "production"
   ```

## 🔧 技术栈

### 后端技术
- **语言**: Java 19+ (利用虚拟线程等新特性)
- **框架**: 
  - Spring Boot 3.x (登录服务器)
  - Netty 4.x (网络通信)
- **序列化**: Google Protocol Buffers 3.x
- **注册中心**: Alibaba Nacos 2.x
- **数据库**: MySQL 8.0+
- **缓存**: Redis 6.0+
- **构建工具**: Maven 3.6+

### 核心依赖
- **Netty**: 高性能网络通信框架
- **Alibaba Nacos**: 服务发现与配置管理
- **Google Protobuf**: 高效数据序列化
- **Apache POI**: Excel文件处理
- **SLF4J + Log4j2**: 日志框架

### 开发工具
- **IDE**: IntelliJ IDEA / Eclipse
- **版本控制**: Git
- **容器化**: Docker (可选)
- **性能监控**: JProfiler / VisualVM

## 📊 性能特性

### 高并发支持
- **虚拟线程**: 利用Java 19+的虚拟线程特性，大幅提升并发处理能力
- **Netty优化**: 基于Netty的事件驱动模型，实现高效的异步IO
- **连接池**: 数据库和Redis连接池优化，减少连接开销

### 低延迟设计
- **异步队列**: 消息异步处理，避免阻塞主线程
- **批量操作**: SQL批量执行优化，提升数据库性能
- **缓存策略**: 多级缓存设计，减少数据库访问

### 可靠性保障
- **序列号校验**: 实现消息序列号校验，防止网络丢包
- **分布式锁**: 基于Redis的分布式锁，保证数据一致性
- **心跳机制**: 服务器间心跳检测，及时发现故障节点

### 扩展性设计
- **微服务架构**: 模块化设计，支持独立部署和扩展
- **服务发现**: 基于Nacos的服务自动发现和注册
- **负载均衡**: 支持多服务器实例的负载分发

## 🤝 支持的游戏类型

### 卡牌游戏
- 卡牌配置管理
- 卡组构建系统
- 战斗逻辑处理
- 抽卡系统

### 肉鸽(Roguelike)游戏
- 随机地图生成
- 角色成长系统
- 道具系统
- 关卡管理

### 棋牌游戏
- 房间匹配系统
- 实时对战逻辑
- 积分排名系统
- 机器人AI

### 扩展支持
框架设计灵活，可通过以下方式扩展支持其他游戏类型：
- 自定义协议定义
- 新增业务逻辑模块
- 扩展配置表结构
- 添加新的服务器类型

## 📄 协议说明

### 消息格式规范
- **双数CMD**: 客户端请求消息 (C2S)
- **单数CMD**: 服务器响应消息 (S2C)  
- **10000-20000**: 服务器间通信专用 (S2S)

### 协议定义示例

#### 登录协议 (Login.proto)
```protobuf
message csLogin {
  string account = 1;      // 账号
  string token = 2;        // 登录令牌
  int64 accountId = 3;     // 账号ID
  int64 playerId = 4;      // 玩家ID
  string gameServerId = 5; // 目标游戏服务器ID
}

message scLogin {
  int32 retCode = 1;       // 返回码
  string message = 2;      // 返回消息
  int64 playerId = 3;      // 玩家ID
}
```

#### 错误消息协议 (ErrorMsg.proto)
```protobuf
enum ErrorCode {
  Ok = 0;                    // 成功
  Failed = 1;                // 失败
  system_error = 2;          // 系统错误
  param_error = 3;           // 参数错误
  account_error = 4;         // 账号错误
  password_error = 5;        // 密码错误
  account_forbidden = 6;     // 账号被禁用
  account_online = 7;        // 账号已登录
  account_not_register = 8;  // 账号未注册
}
```

### 消息处理流程
1. 客户端发送请求消息
2. 网关服务器接收并转发
3. 游戏服务器处理业务逻辑
4. 返回响应消息给客户端

## 📈 压力测试

### SQL性能测试
- **批量执行**: 支持批量SQL执行，提升数据库性能
- **连接池优化**: 针对不同场景优化连接池参数
- **事务管理**: 合理使用事务，平衡性能与一致性

### 并发测试
- **虚拟线程**: 利用虚拟线程提升并发处理能力
- **连接数测试**: 支持数千并发连接
- **内存优化**: 控制内存使用，避免OOM

### 网络性能
- **消息吞吐**: 支持每秒数万条消息处理
- **延迟优化**: 端到端延迟控制在毫秒级别
- **带宽利用**: 高效的数据压缩和序列化

## 🤖 开发理念

### 设计原则
- **高性能**: 利用最新Java特性，追求极致性能
- **可扩展**: 模块化设计，易于扩展新功能
- **易维护**: 清晰的代码结构，完善的文档
- **高可用**: 冗余设计，故障自动恢复

### 架构模式
- **微服务**: 服务拆分，独立部署
- **事件驱动**: 异步处理，提升响应速度
- **配置外置**: 配置与代码分离，便于运维
- **监控集成**: 内置监控点，便于问题定位

## 📄 许可证

此项目遵循 MIT 许可证。详情请参见 LICENSE 文件。

## 🤝 贡献

欢迎提交 Issue 和 Pull Request 来改进项目。

## 📞 支持

如有问题，请通过以下方式联系：
- 提交 GitHub Issue
- 发送邮件至 [邮箱地址]