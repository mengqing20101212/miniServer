# GitHub Copilot instructions for miniServer

Prefer `AGENTS.md` at the repo root as the canonical AI guidance file.

Project facts:
- Java 21 multi-module Maven project
- Aggregator POM: `server/pom.xml`
- Main modules: `config`, `proto`, `tool`, `core`, `LoginServer`, `GameServer`, `GateServer`, `BotServer`
- Core business logic focus is in `server/core`, `server/GameServer`, `server/GateServer`, `server/LoginServer`

Before editing:
1. Read `AGENTS.md`
2. Read `server/doc/module_index.md`
3. Check `STARTUP.SKILL.md` for runtime parameters
4. Treat `docs/JAVA_SOURCE_INDEX.md` as helpful but possibly stale

Editing rules:
- Preserve package structure and existing module boundaries
- Do not replace remote Nacos values from `STARTUP.SKILL.md` with localhost defaults unless the task explicitly requires local-only changes
- Avoid mass changes under `excel/`, `generated-sql/`, and historical logs
- Prefer small, targeted edits with compile-safe reasoning

Useful entrypoints:
- `server/core/src/main/java/ly/ServerContext.java`
- `server/core/src/main/java/ly/net/NetService.java`
- `server/core/src/main/java/ly/rpc/RpcService.java`
- `server/GameServer/src/main/java/ly/GameServer.java`
- `server/GateServer/src/main/java/ly/GateServer.java`
- `server/LoginServer/src/main/java/ly/loginserver/LoginServerApplication.java`
- `server/BotServer/src/main/java/ly/BotServer.java`
