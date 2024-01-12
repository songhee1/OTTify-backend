#!/bin/bash

ROOT_PATH="/home/ubuntu/spring-github-action"
JAR_NAME=$(ls $ROOT_PATH/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$ROOT_PATH/build/libs/$JAR_NAME

APP_LOG="$ROOT_PATH/application.log"
ERROR_LOG="$ROOT_PATH/error.log"
START_LOG="$ROOT_PATH/start.log"
STOP_LOG="$ROOT_PATH/stop.log"

SERVICE_PID=$(pgrep -f $JAR_NAME) # 실행 중인 Spring 서버의 PID

if [ -z $SERVICE_PID ]; then
  echo "서비스 NotFound" >> $STOP_LOG
else
  echo "서비스 종료 " >> $STOP_LOG
  kill -15 $SERVICE_PID
fi

NOW=$(date +%c)

# build 파일 복사
echo "[$NOW] $JAR_NAME 복사" >> $START_LOG
cp $JAR_PATH $ROOT_PATH/$JAR_NAME

# jar 파일 실행
echo "[$NOW] > $JAR_NAME 실행" >> $START_LOG
nohup java -jar $ROOT_PATH/$JAR_NAME > $APP_LOG 2> $ERROR_LOG &

SERVICE_PID=$(pgrep -f $ROOT_PATH/$JAR_NAME)
echo "[$NOW] > 서비스 PID: $SERVICE_PID" >> $START_LOG