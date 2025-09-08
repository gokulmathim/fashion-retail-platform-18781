#!/bin/bash
cd /home/kavia/workspace/code-generation/fashion-retail-platform-18781/garment_shop_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

