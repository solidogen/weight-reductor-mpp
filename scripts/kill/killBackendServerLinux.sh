#!/bin/bash
cd ../..
fuser -n tcp -k 9090
/bin/bash scripts/kill/killH2ServerLinux.sh
echo "backend killed"