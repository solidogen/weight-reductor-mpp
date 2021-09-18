#!/bin/bash

# Deploys frontend app to localhost which connects to remote dev api backend on heroku

cd ../..
echo 'raw_environment = Dev' > environment.properties # CI uses setDevEnvironment.sh script instead
./gradlew frontend:jsBrowserDistribution
./gradlew frontend:jsBrowserDevelopmentRun