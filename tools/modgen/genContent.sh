#!/bin/bash

cd "$(dirname "$0")/../.."

PYTHON_CMD=".venv/bin/python"
if [ ! -f "$PYTHON_CMD" ]; then
    PYTHON_CMD="python3"
fi

echo "Cleaning previous generated content..."
# $PYTHON_CMD tools/modgen/clean_mods.py
./gradlew cleanContent

echo ""
echo "Generating new content..."
./gradlew genContent
