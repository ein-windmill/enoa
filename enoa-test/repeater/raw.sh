#!/bin/bash

source base.sh

#http -f POST ${BASE_URL}"?para=1&para=2&size=10&type=json" para=3 p=1 p=2 p=3 s=a

URL=${BASE_URL}'?para=1&para=2&size=10&type=json'

echo ${RAW_DATA} | http -v POST ${URL} ${COOKIE} Content-Type:text/plain
