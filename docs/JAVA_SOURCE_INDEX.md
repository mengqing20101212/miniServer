# Java Source Index

生成时间: 2026-04-23 23:09:20

Java 文件总数: 189

说明: 该索引按源码路径分组，覆盖仓库内全部 `*.java` 文件。每条记录至少包含路径、包名、主类型和职责归纳，便于后续检索。

## 模块概览

- BotServer: 33 files
- config: 8 files
- core: 80 files
- GameServer: 35 files
- GateServer: 10 files
- LoginServer: 9 files
- proto: 6 files
- redis-test: 1 files
- root: 1 files
- tool: 6 files

## BotServer

### `server\BotServer\src\main\java\ly\bot\command\impl\HeartbeatCommand.java`

- Package: `ly.bot.command.impl`
- Type: `HeartbeatCommand (class)`
- Summary: 机器人命令实现，用于驱动具体动作。

### `server\BotServer\src\main\java\ly\bot\command\impl\LoginCommand.java`

- Package: `ly.bot.command.impl`
- Type: `LoginCommand (class)`
- Summary: 机器人命令实现，用于驱动具体动作。

### `server\BotServer\src\main\java\ly\bot\command\impl\MoveCommand.java`

- Package: `ly.bot.command.impl`
- Type: `MoveCommand (class)`
- Summary: 机器人命令实现，用于驱动具体动作。

### `server\BotServer\src\main\java\ly\bot\command\RobotCommand.java`

- Package: `ly.bot.command`
- Type: `RobotCommand (interface)`
- Summary: BotServer 相关机器人控制代码。

### `server\BotServer\src\main\java\ly\bot\data\impl\ConcurrentModuleDataStore.java`

- Package: `ly.bot.data.impl`
- Type: `ConcurrentModuleDataStore (class)`
- Summary: 机器人运行期数据存储或数据访问封装。

### `server\BotServer\src\main\java\ly\bot\data\ModuleDataStore.java`

- Package: `ly.bot.data`
- Type: `ModuleDataStore (interface)`
- Summary: 机器人运行期数据存储或数据访问封装。

### `server\BotServer\src\main\java\ly\bot\data\RobotSessionDataStore.java`

- Package: `ly.bot.data`
- Type: `RobotSessionDataStore (class)`
- Summary: 机器人运行期数据存储或数据访问封装。

### `server\BotServer\src\main\java\ly\bot\entity\PlayerInfo.java`

- Package: `ly.bot.entity`
- Type: `PlayerInfo (class)`
- Summary: BotServer 相关机器人控制代码。

### `server\BotServer\src\main\java\ly\bot\factory\RobotCommandFactory.java`

- Package: `ly.bot.factory`
- Type: `RobotCommandFactory (class), CommandType (enum)`
- Summary: BotServer 相关机器人控制代码。

### `server\BotServer\src\main\java\ly\bot\http\HttpServerListClient.java`

- Package: `ly.bot.http`
- Type: `HttpServerListClient (class)`
- Summary: BotServer 相关机器人控制代码。

### `server\BotServer\src\main\java\ly\bot\module\impl\CombatModule.java`

- Package: `ly.bot.module.impl`
- Type: `CombatModule (class)`
- Summary: 机器人模块实现，封装某一类自动化行为。

### `server\BotServer\src\main\java\ly\bot\module\impl\CurrencyModule.java`

- Package: `ly.bot.module.impl`
- Type: `CurrencyModule (class)`
- Summary: 机器人模块实现，封装某一类自动化行为。

### `server\BotServer\src\main\java\ly\bot\module\impl\GachaModule.java`

- Package: `ly.bot.module.impl`
- Type: `GachaModule (class)`
- Summary: 机器人模块实现，封装某一类自动化行为。

### `server\BotServer\src\main\java\ly\bot\module\impl\HeartbeatModule.java`

- Package: `ly.bot.module.impl`
- Type: `HeartbeatModule (class)`
- Summary: 机器人模块实现，封装某一类自动化行为。

### `server\BotServer\src\main\java\ly\bot\module\impl\LoginModule.java`

- Package: `ly.bot.module.impl`
- Type: `LoginModule (class)`
- Summary: 机器人模块实现，封装某一类自动化行为。

### `server\BotServer\src\main\java\ly\bot\module\impl\MovementModule.java`

- Package: `ly.bot.module.impl`
- Type: `MovementModule (class)`
- Summary: 机器人模块实现，封装某一类自动化行为。

### `server\BotServer\src\main\java\ly\bot\module\ModuleManager.java`

- Package: `ly.bot.module`
- Type: `ModuleManager (class)`
- Summary: BotServer 相关机器人控制代码。

### `server\BotServer\src\main\java\ly\bot\module\RobotModule.java`

- Package: `ly.bot.module`
- Type: `RobotModule (interface)`
- Summary: BotServer 相关机器人控制代码。

### `server\BotServer\src\main\java\ly\bot\observer\impl\LoggingObserver.java`

- Package: `ly.bot.observer.impl`
- Type: `LoggingObserver (class)`
- Summary: 机器人观察者接口或实现，用于事件监听。

### `server\BotServer\src\main\java\ly\bot\observer\RobotObserver.java`

- Package: `ly.bot.observer`
- Type: `RobotObserver (interface)`
- Summary: 机器人观察者接口或实现，用于事件监听。

### `server\BotServer\src\main\java\ly\bot\RobotManager.java`

- Package: `ly.bot`
- Type: `RobotManager (class)`
- Summary: 机器人整体生命周期与实例管理。

### `server\BotServer\src\main\java\ly\bot\session\RobotSession.java`

- Package: `ly.bot.session`
- Type: `RobotSession (class)`
- Summary: 机器人会话包装，承接网络交互。

### `server\BotServer\src\main\java\ly\bot\state\impl\ConnectedState.java`

- Package: `ly.bot.state.impl`
- Type: `ConnectedState (class)`
- Summary: 机器人状态实现，描述连接或登录阶段。

### `server\BotServer\src\main\java\ly\bot\state\impl\ConnectingState.java`

- Package: `ly.bot.state.impl`
- Type: `ConnectingState (class)`
- Summary: 机器人状态实现，描述连接或登录阶段。

### `server\BotServer\src\main\java\ly\bot\state\impl\LoggedInState.java`

- Package: `ly.bot.state.impl`
- Type: `LoggedInState (class)`
- Summary: 机器人状态实现，描述连接或登录阶段。

### `server\BotServer\src\main\java\ly\bot\state\RobotContext.java`

- Package: `ly.bot.state`
- Type: `RobotContext (class)`
- Summary: BotServer 相关机器人控制代码。

### `server\BotServer\src\main\java\ly\bot\state\RobotState.java`

- Package: `ly.bot.state`
- Type: `RobotState (interface)`
- Summary: BotServer 相关机器人控制代码。

### `server\BotServer\src\main\java\ly\bot\stats\PacketLatencyStats.java`

- Package: `ly.bot.stats`
- Type: `PacketLatencyStats (class)`
- Summary: BotServer 相关机器人控制代码。

### `server\BotServer\src\main\java\ly\bot\strategy\impl\AggressiveBehaviorStrategy.java`

- Package: `ly.bot.strategy.impl`
- Type: `AggressiveBehaviorStrategy (class)`
- Summary: 机器人行为策略接口或实现。

### `server\BotServer\src\main\java\ly\bot\strategy\impl\NormalBehaviorStrategy.java`

- Package: `ly.bot.strategy.impl`
- Type: `NormalBehaviorStrategy (class)`
- Summary: 机器人行为策略接口或实现。

### `server\BotServer\src\main\java\ly\bot\strategy\RobotBehaviorStrategy.java`

- Package: `ly.bot.strategy`
- Type: `RobotBehaviorStrategy (interface)`
- Summary: 机器人行为策略接口或实现。

### `server\BotServer\src\main\java\ly\bot\util\ProtocolTester.java`

- Package: `ly.bot.util`
- Type: `ProtocolTester (class)`
- Summary: BotServer 相关机器人控制代码。

### `server\BotServer\src\main\java\ly\BotServer.java`

- Package: `ly`
- Type: `BotServer (class)`
- Summary: BotServer 相关机器人控制代码。

## config

### `server\config\src\main\java\ly\AbstractConfigManger.java`

- Package: `ly`
- Type: `AbstractConfigManger (class)`
- Summary: 配置表实体或配置管理器。

### `server\config\src\main\java\ly\config\ActivityInfoConfig.java`

- Package: `ly.config`
- Type: `ActivityInfoConfig (class)`
- Summary: 配置表实体或配置管理器。

### `server\config\src\main\java\ly\config\HeroInfoConfig.java`

- Package: `ly.config`
- Type: `HeroInfoConfig (class)`
- Summary: 配置表实体或配置管理器。

### `server\config\src\main\java\ly\config\HeroInfoConfigManager.java`

- Package: `ly.config`
- Type: `HeroInfoConfigManager (class)`
- Summary: 配置表实体或配置管理器。

### `server\config\src\main\java\ly\ConfigLoadException.java`

- Package: `ly`
- Type: `ConfigLoadException (class)`
- Summary: 配置表实体或配置管理器。

### `server\config\src\main\java\ly\ConfigService.java`

- Package: `ly`
- Type: `ConfigService (class), c (Class)`
- Summary: 配置表实体或配置管理器。

### `server\config\src\main\java\ly\InterfaceConfigManagerProxy.java`

- Package: `ly`
- Type: `InterfaceConfigManagerProxy (interface)`
- Summary: 配置表实体或配置管理器。

### `server\config\src\main\java\ly\Main.java`

- Package: `ly`
- Type: `Main (class)`
- Summary: 配置表实体或配置管理器。

## core

### `server\core\src\main\java\ly\cache\CacheService.java`

- Package: `ly.cache`
- Type: `CacheService (class)`
- Summary: 缓存服务封装。

### `server\core\src\main\java\ly\config\DbConfig.java`

- Package: `ly.config`
- Type: `DbConfig (class)`
- Summary: 核心运行配置模型。

### `server\core\src\main\java\ly\config\RedisConfig.java`

- Package: `ly.config`
- Type: `RedisConfig (class)`
- Summary: 核心运行配置模型。

### `server\core\src\main\java\ly\config\RunModuleEnum.java`

- Package: `ly.config`
- Type: `RunModuleEnum (enum)`
- Summary: 核心运行配置模型。

### `server\core\src\main\java\ly\config\ServerConfig.java`

- Package: `ly.config`
- Type: `ServerConfig (class)`
- Summary: 核心运行配置模型。

### `server\core\src\main\java\ly\config\ServerTypeEnum.java`

- Package: `ly.config`
- Type: `ServerTypeEnum (enum)`
- Summary: 核心运行配置模型。

### `server\core\src\main\java\ly\db\AbstractEntry.java`

- Package: `ly.db`
- Type: `AbstractEntry (class)`
- Summary: 数据库实体基类，维护脏标记、保存条件和主键元信息。

### `server\core\src\main\java\ly\db\AutoTableService.java`

- Package: `ly.db`
- Type: `AutoTableService (class)`
- Summary: 根据 Entry 元数据自动建表或校验表结构。

### `server\core\src\main\java\ly\db\DbMeta.java`

- Package: `ly.db`
- Type: `DbMeta (class)`
- Summary: 数据库注解元数据定义。

### `server\core\src\main\java\ly\db\entry\GameItemEntry.java`

- Package: `ly.db.entry`
- Type: `GameItemEntry (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\GameItemEntryHelper.java`

- Package: `ly.db.entry`
- Type: `GameItemEntryHelper (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\LoginEntry.java`

- Package: `ly.db.entry`
- Type: `LoginEntry (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\LoginEntryHelper.java`

- Package: `ly.db.entry`
- Type: `LoginEntryHelper (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\PlayerEntry.java`

- Package: `ly.db.entry`
- Type: `PlayerEntry (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\PlayerEntryHelper.java`

- Package: `ly.db.entry`
- Type: `PlayerEntryHelper (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\ShareDailyEntry.java`

- Package: `ly.db.entry`
- Type: `ShareDailyEntry (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\ShareDailyEntryHelper.java`

- Package: `ly.db.entry`
- Type: `ShareDailyEntryHelper (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\ShareEnumConfigEntry.java`

- Package: `ly.db.entry`
- Type: `ShareEnumConfigEntry (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\ShareEnumConfigEntryHelper.java`

- Package: `ly.db.entry`
- Type: `ShareEnumConfigEntryHelper (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\ShareMonthEntry.java`

- Package: `ly.db.entry`
- Type: `ShareMonthEntry (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\ShareMonthEntryHelper.java`

- Package: `ly.db.entry`
- Type: `ShareMonthEntryHelper (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\ShareWeekEntry.java`

- Package: `ly.db.entry`
- Type: `ShareWeekEntry (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\ShareWeekEntryHelper.java`

- Package: `ly.db.entry`
- Type: `ShareWeekEntryHelper (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\UserInfoEntry.java`

- Package: `ly.db.entry`
- Type: `UserInfoEntry (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\entry\UserInfoEntryHelper.java`

- Package: `ly.db.entry`
- Type: `UserInfoEntryHelper (class)`
- Summary: 数据库 Entry 实体或对应 Helper。

### `server\core\src\main\java\ly\db\MysqlConnector.java`

- Package: `ly.db`
- Type: `MysqlConnector (class)`
- Summary: MySQL 连接与 SQL 执行封装。

### `server\core\src\main\java\ly\db\MysqlService.java`

- Package: `ly.db`
- Type: `MysqlService (class), saveOrUpdateEntry (class)`
- Summary: MySQL 持久化服务，负责保存、更新、查询与结果封装。

### `server\core\src\main\java\ly\EntityToSqlGenerator.java`

- Package: `ly`
- Type: `EntityToSqlGenerator (class)`
- Summary: 项目源码文件。

### `server\core\src\main\java\ly\game\MiniPlayer.java`

- Package: `ly.game`
- Type: `MiniPlayer (class)`
- Summary: 轻量玩家对象与辅助逻辑。

### `server\core\src\main\java\ly\game\MiniPlayerHelper.java`

- Package: `ly.game`
- Type: `MiniPlayerHelper (class)`
- Summary: 轻量玩家对象与辅助逻辑。

### `server\core\src\main\java\ly\IServer.java`

- Package: `ly`
- Type: `IServer (interface)`
- Summary: 服务接口定义。

### `server\core\src\main\java\ly\LoggerDef.java`

- Package: `ly`
- Type: `LoggerDef (class)`
- Summary: 统一日志定义。

### `server\core\src\main\java\ly\Main.java`

- Package: `ly`
- Type: `Main (class)`
- Summary: 核心模块入口或实验入口。

### `server\core\src\main\java\ly\nacos\NacosServerNode.java`

- Package: `ly.nacos`
- Type: `NacosServerNode (class)`
- Summary: Nacos 服务注册与节点信息封装。

### `server\core\src\main\java\ly\nacos\NacosService.java`

- Package: `ly.nacos`
- Type: `NacosService (class)`
- Summary: Nacos 服务注册与节点信息封装。

### `server\core\src\main\java\ly\net\ClientHandler.java`

- Package: `ly.net`
- Type: `ClientHandler (class)`
- Summary: 网络层组件、会话或路由定义。

### `server\core\src\main\java\ly\net\CommonDecoder.java`

- Package: `ly.net`
- Type: `CommonDecoder (class)`
- Summary: 网络层组件、会话或路由定义。

### `server\core\src\main\java\ly\net\CommonEncoder.java`

- Package: `ly.net`
- Type: `CommonEncoder (class)`
- Summary: 网络层组件、会话或路由定义。

### `server\core\src\main\java\ly\net\Connector.java`

- Package: `ly.net`
- Type: `Connector (class)`
- Summary: 网络层组件、会话或路由定义。

### `server\core\src\main\java\ly\net\ConnectSession.java`

- Package: `ly.net`
- Type: `ConnectSession (class)`
- Summary: 连接会话抽象，维护会话状态和收发能力。

### `server\core\src\main\java\ly\net\GameObjectProvider.java`

- Package: `ly.net`
- Type: `GameObjectProvider (interface)`
- Summary: 网络层组件、会话或路由定义。

### `server\core\src\main\java\ly\net\HandlerContext.java`

- Package: `ly.net`
- Type: `HandlerContext (record)`
- Summary: 网络层组件、会话或路由定义。

### `server\core\src\main\java\ly\net\HandlerRouterManager.java`

- Package: `ly.net`
- Type: `HandlerRouterManager (class)`
- Summary: 消息号到控制器/处理器的路由管理。

### `server\core\src\main\java\ly\net\IController.java`

- Package: `ly.net`
- Type: `IController (interface)`
- Summary: 网络层组件、会话或路由定义。

### `server\core\src\main\java\ly\net\IGuidCreator.java`

- Package: `ly.net`
- Type: `IGuidCreator (interface)`
- Summary: 网络层组件、会话或路由定义。

### `server\core\src\main\java\ly\net\IHandlerRouter.java`

- Package: `ly.net`
- Type: `IHandlerRouter (interface)`
- Summary: 网络层组件、会话或路由定义。

### `server\core\src\main\java\ly\net\NetClient.java`

- Package: `ly.net`
- Type: `NetClient (class)`
- Summary: Netty 客户端封装。

### `server\core\src\main\java\ly\net\NetClientManager.java`

- Package: `ly.net`
- Type: `NetClientManager (class)`
- Summary: 网络层组件、会话或路由定义。

### `server\core\src\main\java\ly\net\NetServer.java`

- Package: `ly.net`
- Type: `NetServer (class)`
- Summary: Netty 服务端封装。

### `server\core\src\main\java\ly\net\NetService.java`

- Package: `ly.net`
- Type: `NetService (class)`
- Summary: 网络服务总控，负责启动服务端与客户端网络组件。

### `server\core\src\main\java\ly\net\packet\AbstractMessagePacket.java`

- Package: `ly.net.packet`
- Type: `AbstractMessagePacket (class)`
- Summary: 消息包抽象与工厂。

### `server\core\src\main\java\ly\net\packet\MessagePacketFactory.java`

- Package: `ly.net.packet`
- Type: `MessagePacketFactory (class)`
- Summary: 消息包抽象与工厂。

### `server\core\src\main\java\ly\net\ServerHandler.java`

- Package: `ly.net`
- Type: `ServerHandler (class)`
- Summary: 网络层组件、会话或路由定义。

### `server\core\src\main\java\ly\redis\RedisKeys.java`

- Package: `ly.redis`
- Type: `RedisKeys (enum)`
- Summary: Redis 键定义或工具封装。

### `server\core\src\main\java\ly\redis\RedisUtils.java`

- Package: `ly.redis`
- Type: `RedisUtils (class)`
- Summary: Redis 键定义或工具封装。

### `server\core\src\main\java\ly\rpc\RpcNodeConnector.java`

- Package: `ly.rpc`
- Type: `RpcNodeConnector (class)`
- Summary: RPC 相关工具或连接封装。

### `server\core\src\main\java\ly\rpc\RpcService.java`

- Package: `ly.rpc`
- Type: `RpcService (class)`
- Summary: RPC 服务管理与节点连接协调。

### `server\core\src\main\java\ly\rpc\RpcUtils.java`

- Package: `ly.rpc`
- Type: `RpcUtils (class)`
- Summary: RPC 相关工具或连接封装。

### `server\core\src\main\java\ly\ServerContext.java`

- Package: `ly`
- Type: `ServerContext (class)`
- Summary: 服务启动总入口，组装配置、网络、注册与控制器。

### `server\core\src\main\java\ly\StandaloneServer.java`

- Package: `ly`
- Type: `StandaloneServer (class)`
- Summary: 独立模式服务启动封装。

### `server\core\src\main\java\ly\TestEntityToSql.java`

- Package: `ly`
- Type: `TestEntityToSql (class)`
- Summary: 项目源码文件。

### `server\core\src\main\java\ly\utils\BitSwitchState.java`

- Package: `ly.utils`
- Type: `BitSwitchState (class)`
- Summary: 通用工具类或示例代码。

### `server\core\src\main\java\ly\utils\BitUtils.java`

- Package: `ly.utils`
- Type: `BitUtils (class)`
- Summary: 通用工具类或示例代码。

### `server\core\src\main\java\ly\utils\CommonUtils.java`

- Package: `ly.utils`
- Type: `CommonUtils (class)`
- Summary: 通用工具类或示例代码。

### `server\core\src\main\java\ly\utils\ExcelKVParser.java`

- Package: `ly.utils`
- Type: `ExcelKVParser (class)`
- Summary: 通用工具类或示例代码。

### `server\core\src\main\java\ly\utils\HttpUtils.java`

- Package: `ly.utils`
- Type: `HttpUtils (class)`
- Summary: 通用工具类或示例代码。

### `server\core\src\main\java\ly\utils\KV.java`

- Package: `ly.utils`
- Type: `KV (class)`
- Summary: 通用工具类或示例代码。

### `server\core\src\main\java\ly\utils\KVDemo.java`

- Package: `ly.utils`
- Type: `KVDemo (class)`
- Summary: 通用工具类或示例代码。

### `server\core\src\main\java\ly\utils\RandomUtils.java`

- Package: `ly.utils`
- Type: `RandomUtils (class)`
- Summary: 通用工具类或示例代码。

### `server\core\src\main\java\ly\utils\ThreeGateTest.java`

- Package: `ly.utils`
- Type: `ThreeGateTest (class)`
- Summary: 测试或验证代码。

### `server\core\src\main\java\ly\utils\TimeStatisticsUtils.java`

- Package: `ly.utils`
- Type: `TimeStatisticsUtils (class)`
- Summary: 通用工具类或示例代码。

### `server\core\src\main\java\ly\utils\TimeUtils.java`

- Package: `ly.utils`
- Type: `TimeUtils (class)`
- Summary: 通用工具类或示例代码。

### `server\core\src\main\java\ly\utils\Tuple.java`

- Package: `ly.utils`
- Type: `Tuple (class)`
- Summary: 通用工具类或示例代码。

### `server\core\src\test\java\ly\AutoTableServiceTest.java`

- Package: `ly`
- Type: `AutoTableServiceTest (class)`
- Summary: 测试或验证代码。

### `server\core\src\test\java\ly\db\AbstractEntryDirtyStateTest.java`

- Package: `ly.db`
- Type: `AbstractEntryDirtyStateTest (class)`
- Summary: 测试或验证代码。

### `server\core\src\test\java\ly\db\MysqlServiceTypeConversionTest.java`

- Package: `ly.db`
- Type: `MysqlServiceTypeConversionTest (class)`
- Summary: 测试或验证代码。

### `server\core\src\test\java\ly\utils\ExcelKVExample.java`

- Package: `ly.utils`
- Type: `ExcelKVExample (class)`
- Summary: 测试或验证代码。

### `server\core\src\test\java\ly\utils\KVTest.java`

- Package: `ly.utils`
- Type: `KVTest (class)`
- Summary: 测试或验证代码。

### `server\core\src\test\java\TestClient.java`

- Package: `(default package)`
- Type: `TestClient (class)`
- Summary: 测试或验证代码。

### `server\core\src\test\java\TestServer.java`

- Package: `(default package)`
- Type: `TestServer (class)`
- Summary: 测试或验证代码。

## GameServer

### `server\GameServer\src\main\java\ly\GameClientManager.java`

- Package: `ly`
- Type: `GameClientManager (class)`
- Summary: GameServer 侧客户端连接管理。

### `server\GameServer\src\main\java\ly\GameServer.java`

- Package: `ly`
- Type: `GameServer (class)`
- Summary: GameServer 进程启动入口。

### `server\GameServer\src\main\java\ly\logic\login\GameLogoutController.java`

- Package: `ly.logic.login`
- Type: `GameLogoutController (class)`
- Summary: GameServer 登录链路相关逻辑。

### `server\GameServer\src\main\java\ly\logic\login\GamePlayerLoginController.java`

- Package: `ly.logic.login`
- Type: `GamePlayerLoginController (class)`
- Summary: GameServer 登录链路相关逻辑。

### `server\GameServer\src\main\java\ly\logic\login\LoginManager.java`

- Package: `ly.logic.login`
- Type: `LoginManager (class)`
- Summary: GameServer 登录链路相关逻辑。

### `server\GameServer\src\main\java\ly\logic\login\LoginTask.java`

- Package: `ly.logic.login`
- Type: `LoginTask (class)`
- Summary: GameServer 登录链路相关逻辑。

### `server\GameServer\src\main\java\ly\logic\login\PlayerLogicModule.java`

- Package: `ly.logic.login`
- Type: `PlayerLogicModule (class)`
- Summary: GameServer 登录链路相关逻辑。

### `server\GameServer\src\main\java\ly\logic\ping\PingController.java`

- Package: `ly.logic.ping`
- Type: `PingController (class)`
- Summary: 心跳或连通性检测控制器。

### `server\GameServer\src\main\java\ly\logic\player\AbstractModule.java`

- Package: `ly.logic.player`
- Type: `AbstractModule (class)`
- Summary: 玩家对象、模块和业务数据管理。

### `server\GameServer\src\main\java\ly\logic\player\event\IPlayerEvent.java`

- Package: `ly.logic.player.event`
- Type: `IPlayerEvent (interface)`
- Summary: 玩家事件系统相关定义或管理器。

### `server\GameServer\src\main\java\ly\logic\player\event\PlayerEventManager.java`

- Package: `ly.logic.player.event`
- Type: `PlayerEventManager (class)`
- Summary: 玩家事件系统相关定义或管理器。

### `server\GameServer\src\main\java\ly\logic\player\event\PlayerEventParam.java`

- Package: `ly.logic.player.event`
- Type: `PlayerEventParam (record)`
- Summary: 玩家事件系统相关定义或管理器。

### `server\GameServer\src\main\java\ly\logic\player\event\PlayerEventType.java`

- Package: `ly.logic.player.event`
- Type: `PlayerEventType (enum)`
- Summary: 玩家事件系统相关定义或管理器。

### `server\GameServer\src\main\java\ly\logic\player\Gate2GameRpcGameCallController.java`

- Package: `ly.logic.player`
- Type: `Gate2GameRpcGameCallController (class)`
- Summary: 玩家对象、模块和业务数据管理。

### `server\GameServer\src\main\java\ly\logic\player\IModule.java`

- Package: `ly.logic.player`
- Type: `IModule (interface)`
- Summary: 玩家对象、模块和业务数据管理。

### `server\GameServer\src\main\java\ly\logic\player\ModuleEnum.java`

- Package: `ly.logic.player`
- Type: `ModuleEnum (enum)`
- Summary: 玩家对象、模块和业务数据管理。

### `server\GameServer\src\main\java\ly\logic\player\Player.java`

- Package: `ly.logic.player`
- Type: `Player (class)`
- Summary: 玩家对象、模块和业务数据管理。

### `server\GameServer\src\main\java\ly\logic\player\PlayerConstant.java`

- Package: `ly.logic.player`
- Type: `PlayerConstant (class)`
- Summary: 玩家对象、模块和业务数据管理。

### `server\GameServer\src\main\java\ly\logic\player\PlayerData.java`

- Package: `ly.logic.player`
- Type: `PlayerData (class)`
- Summary: 玩家对象、模块和业务数据管理。

### `server\GameServer\src\main\java\ly\logic\player\PlayerManager.java`

- Package: `ly.logic.player`
- Type: `PlayerManager (class)`
- Summary: 玩家对象、模块和业务数据管理。

### `server\GameServer\src\main\java\ly\logic\player\PlayerModuleData.java`

- Package: `ly.logic.player`
- Type: `PlayerModuleData (class)`
- Summary: 玩家对象、模块和业务数据管理。

### `server\GameServer\src\main\java\ly\logic\player\PlayerStatusEnum.java`

- Package: `ly.logic.player`
- Type: `PlayerStatusEnum (enum)`
- Summary: 玩家对象、模块和业务数据管理。

### `server\GameServer\src\main\java\ly\logic\player\PlayerUtils.java`

- Package: `ly.logic.player`
- Type: `PlayerUtils (class)`
- Summary: 玩家对象、模块和业务数据管理。

### `server\GameServer\src\main\java\ly\net\GameConnectSession.java`

- Package: `ly.net`
- Type: `GameConnectSession (class)`
- Summary: GameServer 网络层上下文、路由或会话实现。

### `server\GameServer\src\main\java\ly\net\GameConnectSessionProvider.java`

- Package: `ly.net`
- Type: `GameConnectSessionProvider (class)`
- Summary: GameServer 网络层上下文、路由或会话实现。

### `server\GameServer\src\main\java\ly\net\GameHandlerContext.java`

- Package: `ly.net`
- Type: `GameHandlerContext (record)`
- Summary: GameServer 网络层上下文、路由或会话实现。

### `server\GameServer\src\main\java\ly\net\GameHandlerRouteManager.java`

- Package: `ly.net`
- Type: `GameHandlerRouteManager (class)`
- Summary: GameServer 网络层上下文、路由或会话实现。

### `server\GameServer\src\main\java\ly\net\GameHandlerRouter.java`

- Package: `ly.net`
- Type: `GameHandlerRouter (interface)`
- Summary: GameServer 网络层上下文、路由或会话实现。

### `server\GameServer\src\main\java\ly\net\GamePlayer.java`

- Package: `ly.net`
- Type: `GamePlayer (class)`
- Summary: GameServer 网络层上下文、路由或会话实现。

### `server\GameServer\src\main\java\ly\net\IGameController.java`

- Package: `ly.net`
- Type: `IGameController (interface)`
- Summary: GameServer 网络层上下文、路由或会话实现。

### `server\GameServer\src\test\java\ly\AppTest.java`

- Package: `ly`
- Type: `AppTest (class)`
- Summary: 测试或验证代码。

### `server\GameServer\src\test\java\ly\DatabaseConnectionTest.java`

- Package: `ly`
- Type: `DatabaseConnectionTest (class)`
- Summary: 测试或验证代码。

### `server\GameServer\src\test\java\ly\NacosConnectionTest.java`

- Package: `ly`
- Type: `NacosConnectionTest (class)`
- Summary: 测试或验证代码。

### `server\GameServer\src\test\java\ly\RedisConnectionTest.java`

- Package: `ly`
- Type: `RedisConnectionTest (class)`
- Summary: 测试或验证代码。

### `server\GameServer\src\test\java\ly\SystemIntegrationTest.java`

- Package: `ly`
- Type: `SystemIntegrationTest (class)`
- Summary: 测试或验证代码。

## GateServer

### `server\GateServer\src\main\java\ly\GateClientManager.java`

- Package: `ly`
- Type: `GateClientManager (class)`
- Summary: GateServer 客户端管理。

### `server\GateServer\src\main\java\ly\GateServer.java`

- Package: `ly`
- Type: `GateServer (class)`
- Summary: GateServer 进程启动入口。

### `server\GateServer\src\main\java\ly\logic\login\GateLoginController.java`

- Package: `ly.logic.login`
- Type: `GateLoginController (class)`
- Summary: GateServer 登录/登出链路控制器。

### `server\GateServer\src\main\java\ly\logic\login\GateLogoutController.java`

- Package: `ly.logic.login`
- Type: `GateLogoutController (class)`
- Summary: GateServer 登录/登出链路控制器。

### `server\GateServer\src\main\java\ly\net\GateClient.java`

- Package: `ly.net`
- Type: `GateClient (class)`
- Summary: GateServer 网络兼容、会话或客户端封装。

### `server\GateServer\src\main\java\ly\net\GateConnectSession.java`

- Package: `ly.net`
- Type: `GateConnectSession (class)`
- Summary: GateServer 网络兼容、会话或客户端封装。

### `server\GateServer\src\main\java\ly\net\GateConnectSessionProvider.java`

- Package: `ly.net`
- Type: `GateConnectSessionProvider (class)`
- Summary: GateServer 网络兼容、会话或客户端封装。

### `server\GateServer\src\main\java\ly\net\IGateController.java`

- Package: `ly.net`
- Type: `IGateController (interface)`
- Summary: GateServer 网络兼容、会话或客户端封装。

### `server\GateServer\src\main\java\ly\net\PacketCompat.java`

- Package: `ly.net`
- Type: `PacketCompat (class)`
- Summary: GateServer 网络兼容、会话或客户端封装。

### `server\GateServer\src\test\java\ly\AppTest.java`

- Package: `ly`
- Type: `AppTest (class)`
- Summary: 测试或验证代码。

## LoginServer

### `server\LoginServer\src\main\java\ly\loginserver\controller\LoginController.java`

- Package: `ly.loginserver.controller`
- Type: `LoginController (class)`
- Summary: 登录服务 HTTP 或业务控制器。

### `server\LoginServer\src\main\java\ly\loginserver\LoginClient.java`

- Package: `ly.loginserver`
- Type: `LoginClient (class)`
- Summary: LoginServer 相关启动、配置或接入代码。

### `server\LoginServer\src\main\java\ly\loginserver\LoginGameObjectProvider.java`

- Package: `ly.loginserver`
- Type: `LoginGameObjectProvider (class)`
- Summary: LoginServer 相关启动、配置或接入代码。

### `server\LoginServer\src\main\java\ly\loginserver\LoginServerApplication.java`

- Package: `ly.loginserver`
- Type: `LoginServerApplication (class)`
- Summary: LoginServer 相关启动、配置或接入代码。

### `server\LoginServer\src\main\java\ly\loginserver\LoginServerConfig.java`

- Package: `ly.loginserver`
- Type: `LoginServerConfig (class)`
- Summary: LoginServer 相关启动、配置或接入代码。

### `server\LoginServer\src\main\java\ly\loginserver\result\ErrorCode.java`

- Package: `ly.loginserver.result`
- Type: `ErrorCode (enum)`
- Summary: 登录接口返回模型或错误码定义。

### `server\LoginServer\src\main\java\ly\loginserver\result\LoginResult.java`

- Package: `ly.loginserver.result`
- Type: `LoginResult (class)`
- Summary: 登录接口返回模型或错误码定义。

### `server\LoginServer\src\main\java\ly\loginserver\result\ServerListResult.java`

- Package: `ly.loginserver.result`
- Type: `ServerListResult (class)`
- Summary: 登录接口返回模型或错误码定义。

### `server\LoginServer\src\main\java\ly\loginserver\service\LoginService.java`

- Package: `ly.loginserver.service`
- Type: `LoginService (class)`
- Summary: 登录服务核心业务逻辑。

## proto

### `server\proto\src\main\java\ly\Main.java`

- Package: `ly`
- Type: `Main (class)`
- Summary: Protobuf 相关生成类或工厂入口。

### `server\proto\src\main\java\ly\proto\Cmd.java`

- Package: `ly.proto`
- Type: `Cmd (class), CMD (enum)`
- Summary: Protobuf 相关生成类或工厂入口。

### `server\proto\src\main\java\ly\proto\ErrorMsg.java`

- Package: `ly.proto`
- Type: `ErrorMsg (class), ErrorCode (enum), csErrorCodeOrBuilder (interface), scErrorCodeOrBuilder (interface)`
- Summary: Protobuf 相关生成类或工厂入口。

### `server\proto\src\main\java\ly\proto\Login.java`

- Package: `ly.proto`
- Type: `Login (class), PlayerInfoOrBuilder (interface), csLoginOrBuilder (interface), scLoginOrBuilder (interface), csLogoutOrBuilder (interface), scLogoutOrBuilder (interface)`
- Summary: Protobuf 相关生成类或工厂入口。

### `server\proto\src\main\java\ly\proto\Server.java`

- Package: `ly.proto`
- Type: `Server (class), ServerMsgType (enum), csServer2ServerOrBuilder (interface), scServer2ServerOrBuilder (interface), csRpcPingOrBuilder (interface), scRpcPingOrBuilder (interface), csGate2GameRpcGameCallOrBuilder (interface), scGate2GameRpcGameCallOrBuilder (interface)`
- Summary: Protobuf 相关生成类或工厂入口。

### `server\proto\src\main\java\ly\ProtoMessageFactory.java`

- Package: `ly`
- Type: `ProtoMessageFactory (class)`
- Summary: Protobuf 相关生成类或工厂入口。

## redis-test

### `redis-test\src\main\java\RedisTest.java`

- Package: `(default package)`
- Type: `RedisTest (class)`
- Summary: 测试或验证代码。

## root

### `test_entity_generator.java`

- Package: `(default package)`
- Type: `test_entity_generator (class)`
- Summary: 项目源码文件。

## tool

### `server\tool\src\main\java\ly\ParserDbEntry.java`

- Package: `ly`
- Type: `ParserDbEntry (class), FiledInfo (class), TableInfo (class)`
- Summary: 代码生成或配置解析工具。

### `server\tool\src\main\java\ly\ParserExcelConfig.java`

- Package: `ly`
- Type: `ParserExcelConfig (class), ExcelConfig (class)`
- Summary: 代码生成或配置解析工具。

### `server\tool\src\main\java\ly\ParserProto.java`

- Package: `ly`
- Type: `ParserProto (class)`
- Summary: 代码生成或配置解析工具。

### `server\tool\src\main\java\ly\ToolMain.java`

- Package: `ly`
- Type: `ToolMain (class)`
- Summary: 工具模块入口或测试代码。

### `server\tool\src\test\java\ly\ParserDbEntryTest.java`

- Package: `ly`
- Type: `ParserDbEntryTest (class)`
- Summary: 测试或验证代码。

### `server\tool\src\test\java\ly\ParserExcelConfigINT2Test.java`

- Package: `ly`
- Type: `ParserExcelConfigINT2Test (class)`
- Summary: 测试或验证代码。

