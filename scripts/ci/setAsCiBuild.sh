#!/bin/bash

# THIS SHOULD ONLY BE RAN BY CI

SCRIPT_DIR=${PWD##*/}
echo $SCRIPT_DIR
if [ "$SCRIPT_DIR" != "weight-reductor-mpp" ]; then
  echo "Not in root"
  cd ../..
else
  echo "In root"
fi

echo 'IS_CI_BUILD = true' > ci-variables.properties
