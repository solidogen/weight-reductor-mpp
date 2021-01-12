#!/bin/bash
cd ..
./gradlew frontend:browserDistribution
netlify deploy