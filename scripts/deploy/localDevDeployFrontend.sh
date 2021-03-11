#!/bin/bash

# Deploys frontend app to localhost which connects to remote dev api backend on heroku

cd ../..
echo 'raw_environment = Dev' > environment.properties # todo change this to invoking setDevEnvironment.sh
./gradlew frontend:browserDistribution
./gradlew frontend:run --continuous