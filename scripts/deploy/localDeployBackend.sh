#!/bin/bash
cd ../..
psql -U postgres -d empty -c "drop database weightreductorunittests;"
psql -U postgres -d empty -c "create database weightreductorunittests;"
./gradlew installGitHook
./gradlew backend:test
./gradlew backend:run
