#!/bin/bash

# Deploys frontend app to localhost which connects to remote prod api backend on heroku

SCRIPT_DIR=${PWD##*/}
echo $SCRIPT_DIR
if [ "$SCRIPT_DIR" != "weight-reductor-mpp" ]; then
  echo "Not in root"
  cd ../..
else
  echo "In root"
fi

/bin/bash scripts/kill/killFrontendServerLinux.sh
/bin/bash scripts/environment/setProdEnvironment.sh
./gradlew frontend:jsBrowserDistribution
./gradlew frontend:jsBrowserDevelopmentRun --continuous