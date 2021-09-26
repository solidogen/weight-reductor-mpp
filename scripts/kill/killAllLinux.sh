#!/bin/bash

SCRIPT_DIR=${PWD##*/}
echo $SCRIPT_DIR
if [ "$SCRIPT_DIR" != "weight-reductor-mpp" ]; then
  echo "Not in root"
  cd ../..
else
  echo "In root"
fi

/bin/bash scripts/kill/killFrontendServerLinux.sh
/bin/bash scripts/kill/killBackendServerLinux.sh
/bin/bash scripts/kill/killH2ServerLinux.sh
echo "all killed"