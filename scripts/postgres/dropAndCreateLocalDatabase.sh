#!/bin/bash
psql -U postgres -d empty -c "drop database weightreductor;"
psql -U postgres -d empty -c "create database weightreductor;"
