#!/bin/bash
fuser -n tcp -k 9100
echo "h2 killed"