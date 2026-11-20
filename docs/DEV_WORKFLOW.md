# miniServer 全链路开发流程

> 本文档由 Hermes Agent 维护，对应 skill: `miniserver-full-dev-workflow`
> 最后更新: 2026-04-27

## 项目概要

miniServer 是一个 Java 25 + Maven 多模块游戏服务器项目。

**repo 路径（WSL）：** `/mnt/d/WORK/me/miniServer`  
**Maven 聚合根：** `server/pom.xml`

## 模块一览

| 模块 | 路径 | 用途 |
|------|------|------|
| config | `server/config/` | 配置类 & 管理器（自动生成） |
| proto | `server/proto/` | Protobuf Java 类 & 工厂 |
| core | `server/core/` | 运行时基础（net/rpc/nacos/mysql/redis） |
| tool | `server/tool/` | Excel→txt→Java 代码生成器 |
| LoginServer | `server/LoginServer/` | HTTP 登录服务 |
| GameServer | `server/GameServer/` | 主游戏逻辑（heroModel 等在这里） |
| GateServer | `server/GateServer/` | 客户端网关 |
| BotServer | `server/BotServer/` | 机器人压力测试 |

## 配置表生成流水线

这是最关键的流程，**Excel 是数据源**：

```
excel/hero*.xlsx
    │  (ParserExcelConfig 读取 Excel Row0=表头, Row2=类型)
    ▼
excel/serverConfig/hero*.txt       ← 导出文本
server/config/src/main/java/ly/config/Hero*Config*.java  ← 生成 Java
```

### Excel 结构约定（ParserExcelConfig 解析规则）

| Excel Row | 内容 |
|-----------|------|
| Row 0 | 服务端表头（字段名） |
| Row 1 | 客户端表头（客户端专用） |
| Row 2 | 类型定义（基础类型、`list<T>`、`map<K,V>`） |
| Row 3 | 注释/描述 |
| Row 4 | 空行 |
| Row 5+ | 数据行（第1列标记 `#` 才被导出） |

### 类型映射

类型仍直接写在 Excel Row 2，不需要额外的类型定义文件。类型名称不区分大小写。

| Excel 类型 | Java 类型 | 单元格示例 |
|-----------|-----------|------|
| `byte` / `short` / `int` / `long` | 对应整数类型 | `100` |
| `float` / `double` | 对应浮点类型 | `1.25` |
| `bool` / `boolean` | `boolean` | `true`、`false`、`1`、`0` |
| `string`（别名 `str`、`text`） | `String` | `1001;2|1002;3` |
| `list<int>` | `List<Integer>` | `1;2;3` |
| `list<string>` | `List<String>` | `普通;稀有` |
| `map<int,long>` | `Map<Integer,Long>` | `1001:200;1002:300` |
| `list<double>` | `List<Double>` | `1.5;2.5` |
| `map<int,bool>` | `Map<Integer,Boolean>` | `1:true;2:false` |

List 用分号 `;` 分隔；Map 每项使用 `键:值`，各项用分号分隔。不需要方括号、花括号或引号。
集合元素前后空白会去掉；单独的 string 字段保留原字符串。
Map 按第一个冒号分隔键和值，因此字符串值可以包含冒号；分号是集合分隔符，字符串键中的冒号也是分隔符，均不提供转义语法。
Map 的键支持整数、布尔和字符串，值和 List 元素只支持基础类型；重复键保留最后一个值。
不支持嵌套 List/Map，复杂配置拆成明细表，通过 ID 关联。不保留 JSON 集合输入格式。
空单元格对应数值 `0`、布尔 `false`、字符串 `""`、空 List 或空 Map。集合不支持 `null` 元素。
字符串不按通用类型系统解析业务结构：掉落、奖励、消耗仍用字符串，由业务代码封装解析，例如
`Award.create(text)`、`Consume.create(text)`。本轮不支持结构体、枚举或配置引用。

转表入口和运行时加载入口共用类型解析规则，错误包含文件、行、字段及集合中的位置。开发阶段类型错误只记录日志并跳过该行，其余数据继续加载。
数值和公式导出保留小数；超出 Excel 数值精度的大整数请使用文本单元格。
本轮采用新集合格式，不保留 `INT1` / `INT2` / `STRING2` 的旧分隔符语法；现有表按需调整后重新生成。
不要手改 Config、Manager、CheckerBase 等生成文件。`buildConfigData` 仅生成数据，修改类型后需要
执行 `parserExcelConfig` 同步生成 Java，并重新构建工具和服务。

当前输出是UTF-8 XML，热更继续整套配置快照替换。
启动时通过固定线程池并行解析各表；全部表加载完成后，依次执行记录级 afterLoad、Checker 和表级 afterLoad，再发布整套快照。热更复用同一加载流程，仍整套替换。

### 重新生成配置（当 Excel 修改后）

```bash
# 1. 构建 tool 模块（Windows JDK 25）
cd /mnt/d/WORK/me/miniServer/server
cmd.exe /c "mvnw.cmd -DskipTests install -pl tool -am"

# 2. 运行生成器
cmd.exe /c "D:\Soft\env\Java\jdk-25\bin\java.exe -jar tool\target\tool-1.0-SNAPSHOT.jar parserExcelConfig ..\excel"
```

**警告：** 重新生成会覆盖 `config` 模块的 Java 代码，并重写 `excel/serverConfig/*.xml` 文件。如果 HeroController 等业务代码引用了旧字段名，需要同步更新。

### 自动生成代码的特征

- Config Java 文件注释：`自动生成的代码 请不要改动`
- 自定义区：`@@@@@自定义方法开始区@@@@@` 和 `@@@@@自定义属性开始区@@@@@` — 这些区域的内容在重新生成时会保留
- Manager 文件的 `reload()` 方法使用 `arr[i]` 位置解析。**列数变更后必须重新生成**，否则会解析错位报错如 `For input string: "1230002,50"`

### 重新生成后的必做检查清单

重新生成后，**不能直接编译通过**，必须依次检查：

1. **KV 循环依赖** — 生成器在 `*Config.java` 和 `*ConfigManager.java` 中硬编码了 `import ly.utils.KV`。
   - config 模块不依赖 core，core 依赖 config → 循环引用
   - 修复：在 `server/config/src/main/java/ly/utils/KV.java` 创建副本（跟 core 里的 KV.java 内容一致）
   - 删除 `*Config.java` 中未使用的 `import ly.utils.KV; import ly.utils.ExcelKVParser;`
   - `*ConfigManager.java` 补齐 `import ly.utils.KV;`（批量 sed 即可）

2. **`isSwitched()` 自动取反 bug** — 生成器模板产出：
   ```java
   return switched.getAndSet(!switched.get());  // ❌ 每次调用都翻转
   ```
   **这是 bug，必须改为：**
   ```java
   return switched.get();  // ✅ 只返回当前值，不翻转
   ```
   - 修复来源：`server/tool/src/main/java/ly/ParserExcelConfig.java` 模板
   - 然后批量修复所有已生成的 `*ConfigManager.java`（345 个文件统一 sed）

3. **自定义方法中的旧字段名** — `@@@@@自定义方法开始区@@@@@` 中的代码如果引用了旧字段名，需要手动更新。常见：
   - `getByModelIdAndStar()` 中 `config.starLevel` → `config.star`
   - `getByHeroAwakenDataAndLevel()` 中 `config.awakenLevel` → `config.sequence`，并补充 `modelName` 过滤

4. **业务代码引用旧字段** — 如 `HeroController` 中 `starCurrencyType` → `currencyType` 等

5. **全量 clean 构建** — `-rf :GameServer` 不够，必须：
   ```bash
   mvnw.cmd clean test -pl GameServer -am
   ```

### 已知字段名变更案例（heroStar）

Excel 改版后旧字段名（过时）→ 新字段名：

| 旧字段名 | 新字段名 | 说明 |
|---------|---------|------|
| starLevel | star | 星级 |
| starCurrencyType | currencyType | 消耗货币类型 |
| starCurrencyNum | currencyNum | 改为 String 格式 |
| starItemId | starItem | 改为 String（逗号分隔） |
| starItemNum | starItem2 | 改为 String |
| allAttributePercent | retainItem | 含义不同 |
| breakThroughStar | (已删除) | 字段不存在 |
| awakenAttrId1-V2 | circuitSlot/followAwaken | 列结构变化 |

## 构建与编译

### 不要使用 WSL 的 Java（WSL 只有 Java 17）
项目需要 **Java 25**，使用 Windows 宿主机 JDK：

```bash
# 完整构建（跳过测试）
cmd.exe /c "cd /d D:\WORK\me\miniServer\server && mvnw.cmd -DskipTests install"
```

### 单模块构建

```bash
# 构建 GameServer 及其依赖
cmd.exe /c "cd /d D:\WORK\me\miniServer\server && mvnw.cmd -DskipTests install -pl GameServer -am"

# clean 构建（解决缓存问题）
cmd.exe /c "cd /d D:\WORK\me\miniServer\server && mvnw.cmd clean install -pl GameServer -am"
```

### 编译错误处理

**`ly.utils.KV` 找不到符号：**  
生成器在 `*Config.java` 和 `*ConfigManager.java` 中硬编码了 `import ly.utils.KV`。config 模块本不依赖 core。

修复方式（已解决）：
- 在 config 模块中创建 `server/config/src/main/java/ly/utils/KV.java` 副本
- 删除 *Config.java 中未使用的 import
- *ConfigManager.java 补齐 `import ly.utils.KV;`（生成器模板缺了这句）

**循环依赖（config ↔ core）：**  
不要在 config/pom.xml 加 core 依赖，Maven 会发现循环。用上述 KV 副本方案解决。

**`-rf :GameServer` 不重建依赖模块：**  
`-rf` 只从指定模块起构建，不会重编 config/core。需要全量 clean 构建。

## 启动与运行流程

启动顺序：`LoginServer → GameServer → SceneServer → GateServer → BotServer`

使用 Windows JDK/mvn：

```bash
# LoginServer（Spring Boot）
cmd.exe /c "cd /d D:\WORK\me\miniServer\server\LoginServer && D:\Soft\env\Java\jdk-25\bin\java.exe -jar target\LoginServer-0.0.1-SNAPSHOT.jar"

# GateServer（mvn exec）
cmd.exe /c "cd /d D:\WORK\me\miniServer\server\GateServer && ..\mvnw.cmd -q exec:java"

# GameServer（mvn exec）
cmd.exe /c "cd /d D:\WORK\me\miniServer\server\GameServer && ..\mvnw.cmd -q exec:java"

# BotServer（验证登录流程）
cmd.exe /c "cd /d D:\WORK\me\miniServer\server\BotServer && D:\Soft\env\Java\jdk-25\bin\java.exe -jar target\BotServer-1.0-SNAPSHOT-shaded.jar --run-bots 127.0.0.1 8889 1"
```

详细启动流程见 `STARTUP.SKILL.md`。

## HeroModel 模块开发

### 模块框架说明

业务模块继承 `AbstractModule`，小状态采用模块级 Protobuf Blob，英雄和资源采用独立 Entity。
业务 setter / 模块方法自动标脏，不调用 `saveData()` 或手动 `update()`。
保存作用域统一准备 Blob 与 Entity 快照，再同步交给 RocksService，MySQL 后台提交。
详细约束见 `docs/PLAYER_MODULE_PERSISTENCE_PLAN.md`。

### 协议消息处理

继承 `IGameController` 接口：

```java
public class HeroController implements IGameController {
    @Override
    public void registerHandlerRouter() {
        gameHandlerRegister(Cmd.CMD.CS_HeroList, this::handleHeroList);
        gameHandlerRegister(Cmd.CMD.CS_HeroStarUp, this::handleHeroStarUp);
        // ...
    }
}
```

### heroUid 生成规则

```
heroUid = playerId * 1000000 + heroId
```

### 升级消费格式

配置表的 `currencyNum` 字段（String 类型）格式为 `slotLevel,cost|slotLevel,cost|...`。
示例：`1,0|2,0|3,0|4,0|5,0|...|10,0`

解析工具方法：`parseStarCurrencyCost()` — 取第一档 slot 的 cost。

## ConfigService 配置加载机制

### init() 类扫描

```java
URL classUrl = ConfigService.class.getClassLoader().getResource("ly/ConfigService.class");
// 提取目录，找子目录 config/ 下的所有 .class 文件
// 实例化所有 InterfaceConfigManagerProxy 实现类
```

**测试环境注意：** `ConfigService.init()` 在测试 JVM 中可能因为 `classUrl.getPath()` 的 URL 路径解析问题而找不到 Manager class 文件（尤其在 Windows 环境下路径有 `file:/D:/...` 格式问题）。测试中不要依赖 `init()` 的自动扫描，改用：

```java
String configDir = new File("../../excel/serverConfig").getAbsolutePath();
ly.ConfigService.getInstance().loadAllConfig(logger, configDir);
```

注意用全限定名 `ly.ConfigService` 避免和 Nacos 的 `com.alibaba.nacos.api.config.ConfigService` 冲突。

### 热加载双实例机制（关键！）

每个 `*ConfigManager` **维护两个内部实现**（instanceImplA / instanceImplB）用于支持热加载。`getInstance()` 通过 `isSwitched()` 标志选择返回哪个：

```
loadConfig(configDir) → 加载到 getInstance() 返回的当前实例
                         （loadConfig 内部调用 getInstance()）
Manager手动切换 →
    manager.isSwitched() → 翻转标志          ← 原来是 bug: 自动取反！
    manager.getInstance() → 现在返回另一个实例
```

### ⚠️ `isSwitched()` 的历史 bug

原始代码（生成器模板）：

```java
public boolean isSwitched() {
    return switched.getAndSet(!switched.get());  // ❌ 每次都翻转
}
```

这个实现的问题是：**每次调用 `getInstance()` 都无副作用地翻转了标志**。在正常的服务端启动流程中，`loadAllConfig()` 只在启动时调用一次，而后续 `getInstance()` 只会被请求处理代码调用一次，所以按顺序调用能恰好拿到正确的实例。但在测试或任何需要连续两次调用 `getInstance()` 的场景中，第二次调用会拿到**另一个（空的）实例**。

**正确做法（已修复）：**

```java
public boolean isSwitched() {
    return switched.get();  // ✅ 只返回当前值，不翻转
}
```

热加载的正确工作流：
1. 收到 GM 热加载指令
2. `new HeroInfoConfigManager().loadConfig(logger, configDir)` → 加载到当前非活跃实例
3. `HeroInfoConfigManager.getInstance().isSwitched()` → 手动翻转，使新数据生效
4. 所有后续调用 `getInstance()` 返回新实例（不再翻转）

## 测试

### 测试运行

```bash
# 全量构建并运行所有测试
cmd.exe /c "cd /d D:\WORK\me\miniServer\server && mvnw.cmd clean test -pl GameServer -am"

# 指定测试类
cmd.exe /c "... -Dtest="ly.logic.hero.HeroControllerTest" -Dsurefire.failIfNoSpecifiedTests=false"

# 指定测试方法
cmd.exe /c "... -Dtest="ly.logic.hero.HeroControllerTest#testHandleHeroStarUp" -Dsurefire.failIfNoSpecifiedTests=false"
```

### 测试注意事项

1. **Config 加载方式** — 测试中不要依赖 `ConfigService.init()` 的类扫描。直接调用：
   ```java
   String configDir = new File("../../excel/serverConfig").getAbsolutePath();
   ly.ConfigService.getInstance().loadAllConfig(logger, configDir);
   ```

2. **heroId 必须真实存在** — 必须使用配置表中存在的英雄 ID，如 `2`（杰诺斯·武装），不能用假 ID（如 `1001`）。

3. **测试资源要充足** — 升级/升星/觉醒的业务逻辑会检查资源是否充足，测试需要添加足够的资源：
   ```java
   resourceModule.addResource(ResourceType.GOLD, 100000);
   resourceModule.addResource(ResourceType.DIAMOND, 10000);
   resourceModule.addResource(ResourceType.EXP_ITEM, 5000);
   resourceModule.addResource(ResourceType.HERO_DEBRIS, 1000);
   resourceModule.addResource(ResourceType.AWAKEN_ITEM, 500);
   resourceModule.addResource(1120001, 50000);  // 觉醒货币
   resourceModule.addResource(1210001, 100);    // 觉醒材料（配置要求 1210001,40）
   ```

4. **saveData() 的 protobuf 问题** — 用 `Unsafe.allocateInstance(PlayerData.class)` 创建 PlayerData（跳过构造函数中的 protobuf），手动初始化字段。

5. **测试用的 heroUid 计算** — mockPlayer 的 playerId=100001，英雄 heroId=2，则 heroUid = 100001 * 1000000 + 2 = 100001000002

### 当前测试覆盖

| 测试类 | 测试数 | 状态 |
|--------|--------|------|
| HeroModuleTest | 7 | 全部通过 |
| ResourceModuleTest | 12 | 全部通过 |
| HeroControllerTest | 9 运行 + 4@Ignore | 全部通过 |
| **合计** | **31** | **27通过，4跳过** |

### 跳过原因

4 个被 @Ignore 的测试都是因为 protobuf 3.21.7 不支持 repeated 字段的 `makeMutableCopy`，需要升级到 3.25+。

## 常用调试命令

### 查看 config 文件列结构
```bash
head -1 excel/serverConfig/heroStar.txt | awk -F'\t' '{for(i=1;i<=NF;i++) print i": "$i}'
```

### 查看某行数据
```bash
awk -F'\t' 'NR==2 { print "col33="  }' excel/serverConfig/heroInfo.txt
```

### 端口检查
```bash
cmd.exe /c "netstat -ano | findstr :8889 && netstat -ano | findstr :9001 && netstat -ano | findstr :9002"
```

## 常见 Pitfalls

1. ❌ **WSL Java 17 不够** — 项目需要 Java 25，永远用 Windows JDK
2. ❌ **直接运行 `mvn` 而不通过 `cmd.exe /c`** — WSL bash 调用 Windows 程序必须用 `cmd.exe /c`
3. ❌ **对自动生成的代码手动加字段** — 应在 `@@@@@自定义区@@@@@` 添加，否则重新生成时被覆盖
4. ❌ **`-rf :GameServer` 不重建依赖** — config/core 改了必须 clean 全量
5. ❌ **测试用假 heroId（如 1001）** — 真实配置中没有此 ID，必须用配置表中存在的 ID
6. ⚠️ **Excel 改列后必须重新生成代码** — 否则旧代码的位置解析会报错
7. ⚠️ **`currencyNum` 是 String 格式** — 不是简单 int，需要专用解析方法
8. ❌ **`ConfigService` 未用全限定名导致 Nacos 冲突** — 测试中要写 `ly.ConfigService`
9. ❌ **生成器模板的 `isSwitched()` 自动取反** — 生成后必须改为 `return switched.get()`
10. ❌ **重新生成后不检查自定义区** — `@@@@@自定义方法开始区@@@@@` 的代码引用了旧字段名，需要手动更新
11. ❌ **`ConfigService.init()` 类扫描依赖环境** — 在 Windows 测试环境中可能找不到 Manager 的 .class 文件

### XML 运行时格式

```xml
<Root>
  <Data id="1" name="普通英雄" costs="1001:200" tags="普通;近战"/>
</Root>
```

加载器按属性名取值，字段顺序无关。集合仍用分号和冒号表达；XML 转义由转表器处理。
GM 上传、配置完整包和热更摘要均使用 `.xml`。类型以 Excel 表头声明为准，不根据单元格内容推断。

### 1200×1200 SLG 地图与基础玩法

SceneServer 使用公司导出的 `WorldMap2.tmx` 和 `world-regions.json`。启动增加：

```text
-Dslg.scene.map-dir=/home/liuyang/.ssh/miniServer/maps/slg
```

正式启动每张地图的执行线程数取自 `regionDefs`，当前为25；不由固定32×32块或X轴条带决定。
AOI/迷雾/寻路的空间方格仍由 `slg.scene.region-size` 指定，默认32。九宫格用 `radius_blocks=1`。

新配置源表为 `excel/slgWorldObject.xlsx`，通过已有转表器生成 `slgWorldObject.xml`，需放到服务配置目录。
`countPerRegion` 控制每个区域的资源点/怪物数量。地图恢复后只补足缺少的配置实例，不覆盖已消耗的存档。

基础行军流程：

1. 客户端发送 `CS_SceneMarchStart`，指定目标ID、坐标及 GATHER/ATTACK。
2. Game绑定真实玩家和服务端部队参数，Scene 读取已保存的主城坐标作为出发点。首次入场按地图标记的出生区域选择可通行空格建城。
3. 发车后直到返程结束，行军始终托管在目标区域；不占用途经格子，不逐格搬迁。
4. 到达资源点后采集，携带量暂按兵量计算；到达怪物后用冻结战力与血量比较，暂不产生战损。
5. 返程到家时 Scene 主动通知 Game 发奖，Game 按行军 ID 记账。已结束行军退出 Tick 和 AOI，仍可用 `CS_SceneMarchQuery` 查看结果。

部队系统尚未接入，Game使用 `slg.march.troops=100`、`slg.march.power=100` 冻结基础部队，忽略客户端兵量和战力。
Scene的 `slg.march.speed=10` 单位为格/秒。采集时长、资源存量、怪物血量、奖励均由新配置表控制。
行军和目标变化继续走已有异步实体持久化，恢复后由目标区域接续状态。

路线与视野相交即可见，起终点可以都在九宫格外。`SC_SceneChanged` 是完整可见快照替换，客户端应删除快照中不再存在的对象。
前端可判断拦截意图；本轮没有拦截协议或实际战斗模块，也没有引入公司的战斗依赖。

沿用现有机器人链路验证（首次运行会注册主账号及后缀 `_observer` 的观察者账号，并分配到指定GameServer）：

```text
--test-slg-gameplay 127.0.0.1 8889 game1001 本次测试账号
```

该入口针对当前公司地图中央区域25，走 Login→Gate→Game→Scene，检查采集与打怪、主城出发、首次查行军前奖励到账、重复查询余额不变，以及第二个玩家在路线中段收到行军出现和结束移除的AOI推送；观察者视野不包含行军两端。

2026-11-02 本地已完成构建、专项测试及1200×1200地图启动。真实机器人验证阻塞在远程Nacos的gRPC订阅超时，尚未跑通完整链路；不能据此认定上述机器人断言已经通过。

剩余联调任务、阻塞证据和复跑上下文见 [待办清单](TODO_AUDIT.md#当前待办slg-完整链路联调)。
