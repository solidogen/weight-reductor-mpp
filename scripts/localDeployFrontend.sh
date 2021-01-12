#!/bin/bash
cd ..
./gradlew frontend:browserDistribution
./gradlew frontend:run --continuous