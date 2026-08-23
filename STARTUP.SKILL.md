---
name: mini-server-startup
description: Canonical local startup parameters, validation rules, and startup order for LoginServer, GameServer, SceneServer, GateServer, and BotServer.
startup:
  nacos:
    url: "118.25.76.117:8848"
    namespace: "ly"
  login:
    serverType: "LOGIN"
    serverId: "login"
    env: "ly"
    netPort: 8888
    springPort: 8889
  game:
    serverType: "GAME"
    serverId: "game1001"
    env: "ly"
    netPort: 9002
  scene:
    serverType: "SCENE"
    serverId: "scene1001"
    env: "ly"
    netPort: 9101
    loadLogSeconds: 60
    slowTickMillis: 200
    pathRegionPadding: 1
    pathParallelism: 4
    pathMaxPending: 10000
    regionMigrationQueueCapacity: 128
    restorePageSize: 1000
    persistencePartitions: 4
  gate:
    serverType: "GATE"
    serverId: "gate1001"
    env: "ly"
    netPort: 9001
  gm:
    serverType: "gm"
    serverId: "gmServer"
    env: "ly"
    netPort: 9088
    springPort: 9090
  bot:
    command: "--run-bots"
    loginHost: "127.0.0.1"
    loginHttpPort: 8889
    numBots: 1
  validation:
    loginSpringPortOffset: 1
    requireBotHttpPortEqualsLoginSpringPort: true
    startupOrder:
      - login
      - game
      - scene
      - gate
      - bot
---

# MiniServer Startup Skill

## Startup Parameters

- Nacos: `118.25.76.117:8848`
- Namespace/env: `ly`
- LoginServer: `serverId=login`, NetServer `8888`, Spring HTTP `8889`
- GameServer: `serverId=game1001`, NetServer `9002`
- SceneServer: `serverId=scene1001`, NetServer `9101`
- SceneServer load/persistence baseline: load log `60s`, slow Tick `200ms`, A* CPU parallelism `4`, A* pending limit `10000`, Region migration queue `128`, restore page `1000`, persistence partitions `4`.
- GateServer: `serverId=gate1001`, NetServer `9001`
- BotServer: `--run-bots 127.0.0.1 8889 1`

## Startup Order

1. `LoginServer`
2. `GameServer`
3. `SceneServer`
4. `GateServer`
5. `BotServer`

Do not start BotServer until Login/Game/Scene/Gate are actually listening on `8888`, `8889`, `9002`, `9101`, and `9001`.

## Validation Rules

- Read this file before starting services.
- `LoginServer.springPort` must equal `LoginServer.netPort + 1`.
- BotServer login HTTP port must equal `LoginServer.springPort`.
- GameServer and GateServer must use the Nacos URL, env, and serverId from this file.
- SceneServer must use the Nacos URL, env, and serverId from this file.
- MySQL, Redis, and business runtime config must come from Nacos, not local `application.properties`.
- If Nacos auth is disabled, do not pass hard-coded Nacos credentials.

## Clean Restart Procedure

- Stop stale Java processes for `LoginServerApplication`, `ly.GameServer`, `ly.SceneServer`, `ly.GateServer`, and `ly.BotServer`.
- Confirm ports `8888`, `8889`, `9001`, `9002`, and `9101` are free before startup.
- Build from `server` with `mvnw.cmd -DskipTests install` on Windows or
  `./mvnw -DskipTests install` on macOS/Linux.
- Generate `cp.txt` for `LoginServer`, `GameServer`, `SceneServer`, `GateServer`, and `BotServer`.
- Prefer a fixed JDK path such as `D:\Soft\env\Java\jdk-25\bin\java.exe`, not mixed `javapath` and direct JDK launches.
- Use fresh log names such as `login-clean.out.log`, `game-clean.out.log`, `gate-clean.out.log`, and `bot-clean.out.log`.
- Check only the current clean logs; historical logs in `runlogs` may contain old failed attempts.

## Success Criteria

- LoginServer listens on `8888` and `8889`.
- GameServer listens on `9002`.
- SceneServer listens on `9101` and has completed map recovery before BotServer starts.
- GateServer listens on `9001`.
- BotServer logs show total robot count `1`, connected Gate count `1`, and login success count `1`.
- LoginServer `/actuator` responds on `http://127.0.0.1:8889/actuator`.
- No current clean log contains route duplicate errors such as `already exist`.
- No current clean log contains repeated Nacos auth errors such as `login failed 404`.

## Runtime Notes

- MySQL may timeout on early Hikari attempts and then recover; this is acceptable only if a later retry logs `database connection success`.
- In the last verified clean run, LoginServer and GateServer connected to MySQL on attempt `1/5`.
- Nacos gRPC errors such as `Request stream error`, `Server check fail`, or `Connection reset` can appear when duplicate stale processes exist or the remote Nacos connection resets. Do a clean restart before treating them as code failures.
- `Nacos Logging don't find adapter`, Netty DNS fallback warnings, and GameServer `SLF4J StaticLoggerBinder` warnings are known startup noise unless accompanied by functional failure.
- `generated-sql/create-tables.sql` files are tracked; do not delete them as temporary files.
