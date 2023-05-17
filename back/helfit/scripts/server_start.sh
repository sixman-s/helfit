#!/usr/bin/env bash
source "$(dirname "$(readlink -f "$0")")/profile.sh"

ROOT_PATH="/home/ubuntu/build"

PROFILE_AND_PORT=$(find_profile_and_port)
ACTIVE_PROFILE=$(echo "${PROFILE_AND_PORT}" | awk '{print $1}')

docker-compose -p spring-${ACTIVE_PROFILE} -f ${ROOT_PATH}/docker-compose.${ACTIVE_PROFILE}.yml up --build --force-recreate -d
