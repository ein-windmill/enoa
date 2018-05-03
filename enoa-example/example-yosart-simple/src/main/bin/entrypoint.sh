#!/bin/sh
#
#
#

JAVA=java

JAVA_OPTS="-Xms512m -Xmx1536m"
PROGRAM_OPTS=""

MAIN_CLASS=io.enoa.example.yosart.simple.YosartSimpleBoot
PROCESS_NAME=yosart-simple

BIN_PATH=$(dirname $(readlink -f $0))
ME="$(basename "$(test -L "$0" && readlink "$0" || echo "$0")")"
HOME_PATH=$(cd ${BIN_PATH}'/../'; pwd)
LIB_PATH=${HOME_PATH}'/lib'
CONF_PATH=${HOME_PATH}'/resources'
PID_FILE=${BIN_PATH}'/'${PROCESS_NAME}'.pid'

_gcpath(){
  res=$1
  for file in `ls $1`
  do
    local path=$1"/"${file}
    if [ -d ${path} ]
    then
      res=${res}':'$(_gcpath ${path})
    else
      res=${res}:${path}
    fi
  done
  echo ${res}
}

cppath(){
 res=
 for parent in $*
  do
    res=${res}':'$(_gcpath ${parent})
  done
 echo ${res}
}


case $1 in
 start)
  echo -n 'Starting worker ... '
  if [ -f ${PID_FILE} ]; then
   if kill -0 `cat ${PID_FILE}` > /dev/null 2>&1; then
    echo ${PROCESS_NAME} already running as process `cat ${PID_FILE}`.
    exit 0
   fi
  fi

  CPPATH=$(cppath ${LIB_PATH} ${CONF_PATH})

  ${JAVA} ${JAVA_OPTS} -cp "${CPPATH}" ${MAIN_CLASS} ${PROGRAM_OPTS}

  if [ $? -eq 0 ]; then
   echo $!
   if  echo -n $! > "$PID_FILE"
   then
    sleep 1
    echo STARTED
   else
    echo FAILED TO WRITE PID
    exit 1
   fi
  else
   echo SERVER DID NOT START
   exit 1
  fi
 ;;
 stop)
  echo -n 'Stopping worker ... '
  if [ ! -f "$PID_FILE" ]
  then
   echo "no worker to stop (could not find file $PID_FILE)"
  else
   echo ${PID_FILE}
   kill -9 $(cat "$PID_FILE")
   rm "$PID_FILE"
   echo STOPPED
  fi
 ;;
 restart)
  shift
  ${BIN_PATH}/${ME} stop ${@}
  sleep 3
  ${BIN_PATH}/${ME} start ${@}
 ;;
 status)
  if [ ! -f "$PID_FILE" ]
  then
    echo "no worker is running."
  else
    echo "worker is running (pid=$PID_FILE)"
  fi
 ;;
 *)
 echo "Usage: $0 {start|stop|restart|status}" >&2
esac
