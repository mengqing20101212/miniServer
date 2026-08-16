#!/usr/bin/env bash
set -euo pipefail
source "$(dirname "$0")/common.sh"
"$(dirname "$0")/doctor.sh"
assert_local_settings
JAVA="$(java_executable)"
mkdir -p "$RUN_DIR" "$LOG_DIR"
PID_FILE="$RUN_DIR/game.pid"
if [[ -f "$PID_FILE" ]]; then
  OLD_PID="$(cat "$PID_FILE")"
  if kill -0 "$OLD_PID" 2>/dev/null; then echo "GameServer already running, pid=$OLD_PID" >&2; exit 1; fi
  rm -f "$PID_FILE"
fi
LOG="$LOG_DIR/game-$(date +%Y%m%d-%H%M%S).log"
read -r -a JVM_OPTIONS <<< "${JAVA_OPTS:--Xms512m -Xmx2g}"
nohup "$JAVA" -Dminiserver.localConfig=true -Dminiserver.projectRoot="$PROJECT_ROOT" \
  "${JVM_OPTIONS[@]}" -jar "$RUNTIME_DIR/game-server.jar" \
  "$NACOS_URL" "$NACOS_NAMESPACE" "$LOCAL_GAME_ID" >"$LOG" 2>&1 &
GAME_PID=$!; echo "$GAME_PID" > "$PID_FILE"
sleep 3
kill -0 "$GAME_PID" 2>/dev/null || { echo "GameServer exited during startup. See $LOG" >&2; exit 1; }
echo "GameServer started. pid=$GAME_PID, log=$LOG"
