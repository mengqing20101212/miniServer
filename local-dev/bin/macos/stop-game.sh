#!/usr/bin/env bash
set -euo pipefail
source "$(dirname "$0")/common.sh"
PID_FILE="$RUN_DIR/game.pid"
[[ -f "$PID_FILE" ]] || { echo 'GameServer is not running.'; exit 0; }
GAME_PID="$(cat "$PID_FILE")"
if ! kill -0 "$GAME_PID" 2>/dev/null; then rm -f "$PID_FILE"; echo 'Removed stale PID file.'; exit 0; fi
COMMAND="$(ps -p "$GAME_PID" -o command=)"
[[ "$COMMAND" == *game-server.jar* ]] || { echo "PID $GAME_PID is not this GameServer; refusing to stop it." >&2; exit 1; }
kill "$GAME_PID"
for _ in {1..15}; do kill -0 "$GAME_PID" 2>/dev/null || break; sleep 1; done
kill -0 "$GAME_PID" 2>/dev/null && kill -9 "$GAME_PID"
rm -f "$PID_FILE"
echo "GameServer stopped. pid=$GAME_PID"
