#!/usr/bin/env bash
source "$(dirname "$(readlink -f "$0")")/profile.sh"

PROFILE_AND_PORT=$(find_profile_and_port)
CURRENT_PROFILE=$(echo "${PROFILE_AND_PORT}" | awk '{print $1}')

sudo docker-compose -p spring-${CURRENT_PROFILE} -f /home/ubuntu/build/docker-compose.${CURRENT_PROFILE}.yml up --build -d
