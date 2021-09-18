#!/bin/bash

# Deploys frontend app to localhost which connects to local backend on localhost

cd ../..
echo 'raw_environment = Local' > environment.properties # CI uses setLocalEnvironment.sh script instead
./gradlew frontend:jsBrowserDistribution
./gradlew frontend:jsBrowserDevelopmentRun