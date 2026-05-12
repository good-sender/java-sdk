#!/usr/bin/env bash
# Regenerate this SDK from the bundled spec at openapi/goodsender.yaml.
#
# Usage:
#   scripts/regen.sh                  # standard regen, preserves LICENSE/README/CHANGELOG
#   scripts/regen.sh --clean          # wipe SDK output first (use after a library: change)
#
# Spec workflow: edit goodsender-web/openapi/goodsender/*.yaml in the spec source repo,
# bundle to a single YAML, copy that bundle into THIS repo's openapi/goodsender.yaml,
# then run this script. The script then runs openapi-generator-cli + the language-specific
# post-process embedded below.

set -euo pipefail

CLEAN=0
for arg in "$@"; do
  case "$arg" in
    --clean) CLEAN=1 ;;
    *) echo "!! unknown arg: $arg" >&2; exit 1 ;;
  esac
done

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SDK_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
SPEC="$SDK_DIR/openapi/goodsender.yaml"
CONFIG="$SCRIPT_DIR/generator-config.yaml"

# Generator JAR version. Override via OPENAPI_GENERATOR_VERSION env var.
# Wrapper version (the npm-published @openapitools/openapi-generator-cli that
# downloads the JAR). Override via OPENAPI_GENERATOR_CLI_VERSION env var.
CLI_WRAPPER_VERSION="${OPENAPI_GENERATOR_CLI_VERSION:-2.21.5}"
export OPENAPI_GENERATOR_VERSION="${OPENAPI_GENERATOR_VERSION:-7.21.0}"

[[ -f "$SPEC" ]] || { echo "!! spec not found at $SPEC" >&2; exit 1; }
[[ -f "$CONFIG" ]] || { echo "!! generator config not found at $CONFIG" >&2; exit 1; }

# --- .openapi-generator-ignore: protect hand-curated files from being overwritten
cat > "$SDK_DIR/.openapi-generator-ignore" <<'IGNORE_EOF'
LICENSE
CHANGELOG.md
README.md
tests/**
scripts/**
openapi/**
openapitools.json
build.gradle
build.sbt
settings.gradle
gradle.properties
gradle/**
gradlew
gradlew.bat
IGNORE_EOF

# --- Optional clean step: wipe SDK output (preserves hand-curated files only)
if (( CLEAN == 1 )); then
  echo ">> --clean: wiping $SDK_DIR (preserving LICENSE, CHANGELOG.md, README.md, tests/, scripts/, openapi/, openapitools.json)"
  find "$SDK_DIR" -mindepth 1 -maxdepth 1 \
    ! -name LICENSE \
    ! -name CHANGELOG.md \
    ! -name README.md \
    ! -name tests \
    ! -name scripts \
    ! -name openapi \
    ! -name openapitools.json \
    ! -name .git \
    ! -name .gitignore \
    -exec rm -rf {} +
fi

# --- Regen via openapi-generator-cli
echo ">> regenerating java SDK"
(cd "$SDK_DIR" && npx --yes @openapitools/openapi-generator-cli@"$CLI_WRAPPER_VERSION" \
  generate \
  -i "$SPEC" \
  -c "$CONFIG" \
  -o "$SDK_DIR" \
  --skip-validate-spec)

# --- Language-specific post-process
# Drop gradle/sbt build files; we publish from Maven (pom.xml). The .openapi-generator-ignore
# above already prevents these from being recreated on subsequent regens, but the first
# regen on a fresh checkout writes them. Remove explicitly.
rm -f "$SDK_DIR/build.gradle" "$SDK_DIR/build.sbt" "$SDK_DIR/settings.gradle" \
      "$SDK_DIR/gradle.properties" "$SDK_DIR/gradlew" "$SDK_DIR/gradlew.bat"
rm -rf "$SDK_DIR/gradle"

# The `native` template doesn't honor httpUserAgent (it's an okhttp-gson only option).
# Inject `User-Agent: goodsender-java/<version>` as a default header on every request
# builder. Idempotent: only inserts if not already present.
ua_version="0.1.0"
ua_header='localVarRequestBuilder.header("User-Agent", "goodsender-java/'"$ua_version"'");'
accept_header='localVarRequestBuilder.header("Accept", "application/json");'
find "$SDK_DIR/src/main/java" -name "*Api.java" -print0 2>/dev/null | while IFS= read -r -d '' f; do
  if ! grep -q 'User-Agent.*goodsender-java' "$f"; then
    perl -i -pe 's|(\Q'"$accept_header"'\E)|$1\n    '"$ua_header"'|g' "$f"
  fi
done

echo ">> done."
