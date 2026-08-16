#!/usr/bin/env bash
set -euo pipefail
source "$(dirname "$0")/common.sh"
mkdir -p "$RUN_DIR" "$LOG_DIR" "$WORK_DIR"
if [[ ! -f "$LOCAL_DEV_DIR/.env" ]]; then
  cp "$LOCAL_DEV_DIR/example.env" "$LOCAL_DEV_DIR/.env"
  echo "Created $LOCAL_DEV_DIR/.env. Fill in the server id assigned by a server developer."
fi
"$(dirname "$0")/doctor.sh" --skip-runtime
