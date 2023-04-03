#!/usr/bin/env bash
cd /home/ubuntu/build

mkdir -p "logs"

LOG_PATH="logs"
LOG_FILE="application.log"
LOGGED=$LOG_PATH/$LOG_FILE

sudo nohup java -Duser.timezone=Asia/Seoul -jar build/libs/helfit-0.0.1-SNAPSHOT.jar >> $LOGGED 2>&1 &
