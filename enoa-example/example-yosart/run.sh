#!/bin/sh
#
#
#

./build.sh

docker run --rm -it --name yosart -p 9102:9102 yosart:1.4-beta
