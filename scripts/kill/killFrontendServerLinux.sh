#!/bin/bash
fuser -n tcp -k 8080
fuser -n tcp -k 8081
fuser -n tcp -k 8082
fuser -n tcp -k 8083
fuser -n tcp -k 8084
fuser -n tcp -k 8085
fuser -n tcp -k 8086
fuser -n tcp -k 8087
fuser -n tcp -k 8088
fuser -n tcp -k 8089
echo "frontend server killed"