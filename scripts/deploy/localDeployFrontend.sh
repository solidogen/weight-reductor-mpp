#!/bin/bash

# Deploys frontend app to localhost which connects to local backend on localhost

cd ../..
echo 'raw_environment = Local' > environment.properties # todo change this to invoking setLocalEnvironment.sh
./gradlew frontend:browserDistribution
./gradlew frontend:run --continuous