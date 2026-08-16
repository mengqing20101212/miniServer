#!/usr/bin/env bash
set -euo pipefail
source "$(dirname "$0")/common.sh"
assert_local_settings
JAVA="$(java_executable)"
assert_java25 "$JAVA"
if [[ "${1:-}" != '--skip-runtime' ]]; then
  [[ -f "$RUNTIME_DIR/game-server.jar" ]] || { echo 'Missing game-server.jar' >&2; exit 1; }
  [[ -f "$RUNTIME_DIR/config-builder.jar" ]] || { echo 'Missing config-builder.jar' >&2; exit 1; }
  compgen -G "$PROJECT_ROOT/excel/serverConfig/*.txt" >/dev/null || { echo 'No generated TXT configs.' >&2; exit 1; }
fi
NACOS_ENDPOINT="${NACOS_URL#http://}"; NACOS_ENDPOINT="${NACOS_ENDPOINT#https://}"
NACOS_HOST="${NACOS_ENDPOINT%%:*}"; NACOS_PORT="${NACOS_ENDPOINT##*:}"
[[ "$NACOS_PORT" != "$NACOS_ENDPOINT" ]] || NACOS_PORT=8848
nc -z -w 3 "$NACOS_HOST" "$NACOS_PORT" || { echo "Nacos is unreachable: $NACOS_URL" >&2; exit 1; }
echo "OK: Java 25, settings, and Nacos connectivity. serverId=$LOCAL_GAME_ID"
