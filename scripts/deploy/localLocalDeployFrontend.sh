#!/bin/bash

# Deploys frontend app to localhost which connects to local backend on localhost

cd ../..
/bin/bash scripts/kill/killFrontendServerLinux.sh
/bin/bash scripts/environment/setLocalEnvironment.sh
./gradlew frontend:jsBrowserDistribution
./gradlew frontend:jsBrowserDevelopmentRun --continuous