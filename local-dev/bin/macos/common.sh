#!/usr/bin/env bash
set -euo pipefail

LOCAL_DEV_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
PROJECT_ROOT="$(cd "$LOCAL_DEV_DIR/.." && pwd)"
RUNTIME_DIR="$LOCAL_DEV_DIR/runtime"
RUN_DIR="$LOCAL_DEV_DIR/run"
LOG_DIR="$LOCAL_DEV_DIR/logs"
WORK_DIR="$LOCAL_DEV_DIR/work"

load_local_env() {
  local env_file="$LOCAL_DEV_DIR/.env"
  [[ -f "$env_file" ]] || { echo "Missing $env_file. Run setup.sh first." >&2; exit 1; }
  set -a
  # shellcheck disable=SC1090
  source "$env_file"
  set +a
}

java_executable() {
  if [[ -n "${JAVA_HOME:-}" && -x "$JAVA_HOME/bin/java" ]]; then
    printf '%s\n' "$JAVA_HOME/bin/java"
  elif command -v java >/dev/null 2>&1; then
    command -v java
  else
    echo 'JDK 25 was not found. Set JAVA_HOME in local-dev/.env.' >&2
    exit 1
  fi
}

assert_java25() {
  local java="$1" version
  version="$($java -version 2>&1 | head -n 1)"
  [[ "$version" =~ version\ \"25([.\"-]) ]] || { echo "JDK 25 is required, current: $version" >&2; exit 1; }
}

assert_local_settings() {
  load_local_env
  [[ "${LOCAL_GAME_ID:-}" =~ ^game-local-[a-zA-Z0-9_-]+$ ]] || {
    echo 'LOCAL_GAME_ID must be the game-local-* id assigned by a server developer.' >&2; exit 1;
  }
  [[ -n "${NACOS_URL:-}" && -n "${NACOS_NAMESPACE:-}" ]] || {
    echo 'NACOS_URL and NACOS_NAMESPACE are required.' >&2; exit 1;
  }
}
