#!/bin/sh

BIN_PATH=$(dirname $(readlink -f $0))

docker run --rm --name apidoc -v ${BIN_PATH}:/apidoc -it tntest/apidoc \
 apidoc \
 -c /apidoc \
 -i /apidoc/src/main/java \
 -o /apidoc/tmp \
 --line-ending LF
