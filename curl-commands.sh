#!/bin/bash
set -e
set -x

curl http://localhost:8082/impressions/by_device

curl http://localhost:8082/impressions/by_dayofweek

curl http://localhost:8082/impressions/by_dayofmonth

curl http://localhost:8082/impressions/by_hour