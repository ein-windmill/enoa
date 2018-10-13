#!/bin/bash

BIN_PATH=$(dirname $(readlink -f $0))
VERSION=

TARGET=zh-tw
ORIGIN=zh-cn
DOCKER='docker -H localhost:2375'

OPENCC=

_gcpath(){
  res=$1
  for file in `ls $1`
  do
    local path=$1"/"$file
    if [ -d ${path} ]
    then
      _gcpath ${path}
    else
      if [[ ${path} = *"_book"* || ${path} = *"node_modules"* ]]; then
        continue
      fi
      local SUFFIX=${path##*.}
      if [[ "$SUFFIX"x != "md"x && "$SUFFIX"x != "MD"x ]]; then
        continue
      fi
      local _TARGET=`echo ${path} | sed "s/${ORIGIN}/${TARGET}/g"`
      local PATH_TARGET=${_TARGET%/*}
      mkdir -p ${PATH_TARGET}

      local ORI0=${_TARGET}
      local ORI1=${ORI0//\//\\/}

      local FILE_TARGET=${path//"$BIN_PATH/$VERSION/$ORIGIN"/''}
      local IN=/data/origin${FILE_TARGET}
      local OUT=/data/target${FILE_TARGET}
#      echo "$IN -> $OUT";

#      ${OPENCC} -c tw2sp -i ${IN} -o ${OUT}
#      echo opencc -c tw2sp -i ${path} -o `echo ${path} | sed "s/${_TARGET}/${ORIGIN}/g"`

      echo opencc -c s2twp -i ${path} -o ${_TARGET}
      ${OPENCC} -c s2twp -i ${IN} -o ${OUT}
    fi
  done
}

cppath(){
 for parent in $*
  do
   _gcpath ${parent}
  done
}

start(){
  # find -name _book | xargs rm -rf
#  cppath ${BIN_PATH}/${ZH_TW}

  VERSION=$1
  echo "${BIN_PATH}/${VERSION}/${ORIGIN}";
  OPENCC="${DOCKER} run --rm --name occ -v ${BIN_PATH}/${VERSION}/${ORIGIN}:/data/origin -v ${BIN_PATH}/${VERSION}/${TARGET}:/data/target 1docker/opencc opencc"
#  OPENCC="${DOCKER} run --rm --name occ -v /d/dev/enoa/enoa/wiki/1.6.2/zh-cn:/data/origin -v /d/dev/enoa/enoa/wiki/1.6.2/zh-tw:/data/target 1docker/opencc opencc"
  DOC_PATH=${BIN_PATH}/${VERSION}/${ORIGIN}
  if [ ! -d "${DOC_PATH}" ]; then
    die 'not have this path -> '${DOC_PATH}
    exit 0
  fi

#  echo ${DOC_PATH}
  cppath ${DOC_PATH}
}

die(){
  echo $1
  echo '==============='
  echo DIE
  exit 0
}

start $@

