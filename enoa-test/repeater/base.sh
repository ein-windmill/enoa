#!/bin/sh


BIN_PATH=$(dirname $(readlink -f $0))

BASE_URL='http://localhost:9001/example/test'


## fastci 测试使用, nginx 端口 80
#BASE_URL='http://localhost/example/test'

COOKIE='Cookie:valued-visitor=yes;foo=bar'

RAW_DATA='raw body'

clear
