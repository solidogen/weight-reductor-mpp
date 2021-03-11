#!/bin/bash

# THIS SHOULD ONLY BE RAN BY CI

cd ../..
echo 'IS_CI_BUILD = true' > ci-variables.properties
