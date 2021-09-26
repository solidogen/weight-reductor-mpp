#!/bin/bash

# Deploys backend app running on localhost. All remote deploys are done by heroku atm.

SCRIPT_DIR=${PWD##*/}
echo $SCRIPT_DIR
if [ "$SCRIPT_DIR" != "weight-reductor-mpp" ]; then
  echo "Not in root"
  cd ../..
else
  echo "In root"
fi

/bin/bash scripts/kill/killFrontendServerLinux.sh
psql -U postgres -d empty -c "drop database weightreductorunittests;"
psql -U postgres -d empty -c "create database weightreductorunittests;"
./gradlew installGitHook
./gradlew backend:test
./gradlew backend:run
