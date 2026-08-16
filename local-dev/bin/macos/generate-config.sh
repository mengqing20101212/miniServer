#!/usr/bin/env bash
set -euo pipefail
source "$(dirname "$0")/common.sh"
assert_local_settings
JAVA="$(java_executable)"; assert_java25 "$JAVA"
BUILDER="$RUNTIME_DIR/config-builder.jar"
[[ -f "$BUILDER" ]] || { echo "Missing $BUILDER" >&2; exit 1; }
TARGET="$PROJECT_ROOT/excel/serverConfig"
STAGING="$WORK_DIR/serverConfig-staging"
BACKUP="$WORK_DIR/serverConfig-backup"
rm -rf "$STAGING" "$BACKUP"; mkdir -p "$STAGING"
"$JAVA" -jar "$BUILDER" buildConfigData "$PROJECT_ROOT/excel" "$STAGING"
compgen -G "$STAGING/*.txt" >/dev/null || { echo 'No TXT files were generated.' >&2; exit 1; }
[[ ! -d "$TARGET" ]] || mv "$TARGET" "$BACKUP"
if mv "$STAGING" "$TARGET"; then
  rm -rf "$BACKUP"
else
  [[ ! -d "$BACKUP" ]] || mv "$BACKUP" "$TARGET"
  exit 1
fi
echo "Generated config: $TARGET"
