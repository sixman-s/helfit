#!/usr/bin/env bash
cd /home/ubuntu/build

source "$(dirname "$(readlink -f "$0")")/profile.sh"

PROFILE_AND_PORT=$(find_profile_and_port)
CURRENT_PROFILE=$(echo "${PROFILE_AND_PORT}" | awk '{print $1}')

sudo docker-compose -p spring-${CURRENT_PROFILE} -f ./docker-compose.%{CURRENT_PROFILE}.yml up --build -d
