#!/bin/bash

# Deploys frontend app to localhost which connects to remote prod api backend on heroku

cd ../..
echo 'raw_environment = Prod' > environment.properties # CI uses setProdEnvironment.sh script instead
./gradlew frontend:jsBrowserDistribution
./gradlew frontend:jsBrowserDevelopmentRun