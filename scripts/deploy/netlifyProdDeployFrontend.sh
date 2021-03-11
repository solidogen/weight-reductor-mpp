#!/bin/bash

# Deploys frontend app to netlify prod url which connects to remote prod api backend on heroku

cd ../..
echo 'raw_environment = Prod' > environment.properties # todo change this to invoking setProdEnvironment.sh
./gradlew frontend:browserDistribution
netlify deploy --prod