#!/bin/bash

source base.sh

URL=${BASE_URL}'?para=1&para=2&size=10&type=json&method=get'

http -v GET ${URL} ${COOKIE}
