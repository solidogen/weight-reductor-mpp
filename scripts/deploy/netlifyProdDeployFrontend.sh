#!/bin/bash

# Deploys frontend app to netlify prod url which connects to remote prod api backend on heroku

cd ../..
/bin/bash scripts/environment/setProdEnvironment.sh
./gradlew frontend:jsBrowserDistribution
netlify deploy --prod