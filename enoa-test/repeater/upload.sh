#!/bin/sh

source base.sh
URL=${BASE_URL}'?para=1&para=2&size=10&type=json'

http -v -f POST ${URL} ${COOKIE} para=3 p=a p=b p=c s=a f@${BIN_PATH}/file.txt f@${BIN_PATH}/file.txt e@`which ls`
