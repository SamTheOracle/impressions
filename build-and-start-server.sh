#!/bin/bash

set -e
set -x
cd data
mvn clean install
cd ..
cd server
./mvnw package
cd ..

docker build -t impressions .
docker run --rm --name impressions_server -p 8082:8080 -e DATASET_PATH=dataset.csv -v "$PWD"/dataset.csv:/deployments/dataset.csv impressions