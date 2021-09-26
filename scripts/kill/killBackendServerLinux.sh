#!/bin/bash

SCRIPT_DIR=${PWD##*/}
echo $SCRIPT_DIR
if [ "$SCRIPT_DIR" != "weight-reductor-mpp" ]; then
  echo "Not in root"
  cd ../..
else
  echo "In root"
fi

fuser -n tcp -k 9090
/bin/bash scripts/kill/killH2ServerLinux.sh
echo "backend killed"