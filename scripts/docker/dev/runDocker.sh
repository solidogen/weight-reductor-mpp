#!/bin/bash
sudo service postgresql stop
sudo service docker restart
docker-compose up