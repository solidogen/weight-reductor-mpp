#!/bin/bash

# Deploys frontend app to netlify draft url which connects to remote dev api backend on heroku

cd ../..
/bin/bash scripts/environment/setDevEnvironment.sh
./gradlew frontend:jsBrowserDistribution
netlify deploy