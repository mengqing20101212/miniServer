# MiniServer - QWEN.md

## Project Overview

**MiniServer** is a high-performance distributed game server framework built with Java 25, designed for card games, Roguelike games, and board/card games. It uses a microservices architecture with Nacos for service discovery, Netty for networking, and supports virtual threads for enhanced concurrency.

### Core Technologies
- **Language**: Java 25 (virtual threads)
- **Frameworks**: Spring Boot 3.x (LoginServer), Netty 4.x
- **Service Discovery**: Alibaba Nacos 2.x
- **Database**: MySQL 8.0+, Redis 6.0+
- **Serialization**: Google Protocol Buffers 3.x
- **Build Tool**: Maven 3.6+

### Server Types
| Server | Port | Purpose |
|--------|------|---------|
| LoginServer | 8888 (Net), 8889 (HTTP) | Authentication, session management, Spring Boot app |
| GameServer | 9002 | Game logic, player lifecycle, business logic |
| GateServer | 9001 | Client gateway, message routing, connection management |
| BotServer | - | Robot client for load testing and protocol verification |
| GMServer | 9090 | Game management backend, admin/role/menu pages, operation logs |

### Architecture
- **Nacos**: `118.25.76.117:8848`, namespace: `ly`
- **Startup Order**: LoginServer → GameServer → GateServer → BotServer
- **Module Structure**: Multi-module Maven project under `server/`

## Building and Running

### Environment Requirements
- Java 25 (JDK 25)
- Maven 3.6+
- Access to Nacos, MySQL, Redis

### Build Commands

```bash
# Full build (from server directory)
cd server
mvn -DskipTests install

# Quick compile
cd server
mvn clean compile -DskipTests
```

### Running Servers

All servers read startup parameters from `STARTUP.SKILL.md` automatically.

**LoginServer** (Spring Boot):
```bash
cd server/LoginServer
# Uses STARTUP.SKILL.md defaults automatically
# Or run via Maven: mvn spring-boot:run
```

**GameServer**:
```bash
cd server/GameServer
java -cp target/gameserver-1.0-SNAPSHOT.jar ly.GameServer 118.25.76.117:8848 ly game1001
```

**GateServer**:
```bash
cd server/GateServer
java -cp target/gateserver-1.0-SNAPSHOT.jar ly.GateServer 118.25.76.117:8848 ly gate1001
```

**BotServer**:
```bash
cd server/BotServer
java -cp target/botserver-1.0-SNAPSHOT.jar ly.BotServer --run-bots 127.0.0.1 8889 1
```

### Startup Validation
After starting, verify:
- LoginServer listens on ports 8888 and 8889
- GameServer listens on port 9002
- GateServer listens on port 9001
- `http://127.0.0.1:8889/actuator` is accessible
- BotServer logs show successful robot connections and logins

## Project Structure

```
miniServer/
├── excel/                 # Excel configuration tables (source)
├── generated-sql/         # Generated SQL scripts
├── proto/                 # .proto protocol definitions
├── server/                # Main source code
│   ├── config/            # Configuration models and managers
│   ├── core/              # Core runtime (net/rpc/nacos/mysql/redis)
│   ├── GameServer/        # Game logic server
│   ├── GateServer/        # Gateway server
│   ├── LoginServer/       # Login server (Spring Boot)
│   ├── BotServer/         # Bot/load testing server
│   ├── GMServer/          # GM/admin backend (Spring Boot)
│   ├── proto/             # Generated protobuf Java classes
│   ├── tool/              # Excel/proto/DB generation tools
│   └── pom.xml            # Aggregator POM
├── logs/                  # Runtime logs
├── runlogs/               # Server startup logs
├── docs/                  # Documentation
│   ├── AI_PROJECT_INDEX.md
│   ├── DEV_WORKFLOW.md
│   └── JAVA_SOURCE_INDEX.md
├── STARTUP.SKILL.md       # Canonical startup parameters
└── README.md              # Project overview
```

## Development Conventions

### Code Style
- Follow existing Java naming conventions in the codebase
- Use SLF4J for logging (`LoggerDef.SystemLogger` or module-specific loggers)
- Configuration classes follow the pattern: `XxxConfig` (data) and `XxxConfigManager` (manager)

### Key Entry Points for Code Reading
1. `server/core/src/main/java/ly/ServerContext.java` - Server lifecycle and global state
2. `server/core/src/main/java/ly/net/NetService.java` - Network service management
3. `server/core/src/main/java/ly/rpc/RpcService.java` - RPC communication
4. `server/GameServer/src/main/java/ly/GameServer.java` - Game server entry
5. `server/GateServer/src/main/java/ly/GateServer.java` - Gateway entry
6. `server/LoginServer/src/main/java/ly/loginserver/LoginServerApplication.java` - Login entry
7. `server/GMServer/src/main/java/ly/gmserver/GMServerApplication.java` - GM backend entry
8. `server/tool/src/main/java/ly/ToolMain.java` - Generation tools entry

### Configuration Generation
Excel tables are auto-converted to Java config classes:
```bash
cd server/tool
mvn compile exec:java -Dexec.mainClass="ly.ToolMain" -Dexec.args="parserExcelConfig ../../excel"
```

### Testing
- Tests are skipped by default in builds (`-DskipTests`)
- Test files exist in `src/test/java` directories
- BotServer provides runtime protocol testing capabilities

### Important Constraints
- **DO NOT** modify `STARTUP.SKILL.md` parameters unless you understand the full impact
- **DO NOT** delete `generated-sql/create-tables.sql` - it's tracked output
- **DO NOT** casually edit `excel/` files without understanding the generation chain
- BotServer `loginHttpPort` MUST equal LoginServer `springPort` (8889)
- LoginServer `springPort` MUST equal `netPort + 1` (8888 + 1 = 8889)

## VS Code Development

### Available Launch Configurations
- `Debug LoginServer`
- `Debug GameServer`
- `Debug GateServer`
- `Debug BotServer`
- `Debug Backend Core` (combination)
- `Debug Full Stack With Bot` (full stack)

### Reindex Command
If Java projects become stale:
```bash
# Run from project root
reindex_vscode.bat
```
This clears VS Code Java caches and `target/` directories, then prompts for "Rescan Java Projects".

## Documentation References
- `README.md` - Full project overview and architecture
- `STARTUP.SKILL.md` - Authoritative startup parameters and validation rules
- `docs/DEV_WORKFLOW.md` - Detailed build/run/debug workflow
- `server/doc/module_index.md` - Module-level code guide
- `docs/JAVA_SOURCE_INDEX.md` - Live Java file index (auto-generated)
