#!/usr/bin/env bash
function find_profile_and_port() {
    IS_ACTIVE_BLUE=$(docker ps --filter "name=spring-blue" --format "{{.Names}}")
    # IS_ACTIVE_BLUE=$(docker-compose -p spring-blue -f docker-compose.blue.yaml ps | grep Up)

    if [ -z "$IS_ACTIVE_BLUE" ]; then
        ACTIVE_PROFILE="blue"
        ACTIVE_PORT=8080

        IDLE_PROFILE="green"
        IDLE_PORT=8081
    else
        ACTIVE_PROFILE="green"
        ACTIVE_PORT=8081

        IDLE_PROFILE="blue"
        IDLE_PORT=8080
    fi

    echo "${ACTIVE_PROFILE} ${ACTIVE_PORT} ${IDLE_PROFILE} ${IDLE_PORT}"
}
