#!/bin/sh
#
#
#

./build.sh

docker run --rm -it --name yosart-simple -p 9102:9102 yosart-simple:1.3-beta
