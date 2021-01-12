#!/bin/bash
lsof -nti:9090 | xargs kill -9