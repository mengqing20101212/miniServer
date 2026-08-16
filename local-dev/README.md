# Personal GameServer

This directory starts the GameServer assigned to a planner or tester. LoginServer, GateServer,
GMServer, Nacos, MySQL, and Redis are maintained by server developers in the shared intranet.

## Prerequisites

1. Ask a server developer for a `game-local-*` server id and bound test account.
2. Install a full JDK 25.
3. Copy `example.env` to `.env` and set the assigned id, Nacos address, and namespace.

## Windows

```powershell
.\local-dev\bin\windows\setup.ps1
.\local-dev\bin\windows\generate-config.ps1
.\local-dev\bin\windows\start-game.ps1
```

Stop or restart with `stop-game.ps1` and `restart-game.ps1`. Run `doctor.ps1` when startup fails.

## macOS

```bash
chmod +x local-dev/bin/macos/*.sh
./local-dev/bin/macos/setup.sh
./local-dev/bin/macos/generate-config.sh
./local-dev/bin/macos/start-game.sh
```

The scripts never compile Java. Developers update `runtime/game-server.jar`,
`runtime/config-builder.jar`, and `runtime/lib` when server code changes.

To test time-dependent behavior, stop GameServer, change the operating-system time explicitly,
then restart it. Restore automatic time synchronization after the test.
