#!/bin/bash

# Deploys frontend app to localhost which connects to remote dev api backend on heroku

cd ../..
/bin/bash scripts/kill/killFrontendServerLinux.sh
/bin/bash scripts/environment/setDevEnvironment.sh
./gradlew frontend:jsBrowserDistribution
./gradlew frontend:jsBrowserDevelopmentRun