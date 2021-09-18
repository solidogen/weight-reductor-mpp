#!/bin/bash

# Deploys backend app running on localhost. All remote deploys are done by heroku atm.

cd ../..
/bin/bash scripts/kill/killFrontendServerLinux.sh
psql -U postgres -d empty -c "drop database weightreductorunittests;"
psql -U postgres -d empty -c "create database weightreductorunittests;"
./gradlew installGitHook
./gradlew backend:test
./gradlew backend:run
