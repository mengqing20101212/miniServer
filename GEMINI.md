# GEMINI.md

Canonical project instructions live in `AGENTS.md`.

Minimal repo map:
- Root docs: `README.md`, `AGENTS.md`, `STARTUP.SKILL.md`
- Main source tree: `server/`
- Config spreadsheets: `excel/`
- Protobuf sources: `proto/`
- Logs: `logs/`, `runlogs/`

Most important modules:
- `server/core`
- `server/GameServer`
- `server/GateServer`
- `server/LoginServer`
- `server/BotServer`

Index references:
- `docs/AI_PROJECT_INDEX.md`
- `docs/JAVA_SOURCE_INDEX.md`
- `server/doc/module_index.md`

Runtime note:
Prefer `STARTUP.SKILL.md` over older localhost-only notes when startup parameters disagree.
