#!/bin/bash

# Deploys backend app running on localhost. All remote deploys are done by heroku atm.

cd ../..
fuser -n tcp -k 9090 # THIS IS LINUX ONLY, remove if needed. kept for convenience
fuser -n tcp -k 9100 # THIS IS LINUX ONLY, remove if needed. kept for convenience
psql -U postgres -d empty -c "drop database weightreductorunittests;"
psql -U postgres -d empty -c "create database weightreductorunittests;"
./gradlew installGitHook
./gradlew backend:test
./gradlew backend:run
