#!/bin/sh
#
#
#

./build.sh

docker run --rm -it --name yosart-simple -p 9102:9102 yosart-simple:1.7.2-rc
