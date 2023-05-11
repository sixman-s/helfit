#!/usr/bin/env bash
source "$(dirname "$(readlink -f "$0")")/profile.sh"

ROOT_PATH="/home/ubuntu/build"

PROFILE_AND_PORT=$(find_profile_and_port)
ACTIVE_PROFILE=$(echo "${PROFILE_AND_PORT}" | awk '{print $1}')
ACTIVE_PORT=$(echo "${PROFILE_AND_PORT}" | awk '{print $2}')
IDLE_PROFILE=$(echo "${PROFILE_AND_PORT}" | awk '{print $3}')
IDLE_PORT=$(echo "${PROFILE_AND_PORT}" | awk '{print $4}')

for cnt in {1..10}
do
    echo "서버 응답 대기중 ... (${cnt}/10)";

    UP=$(curl -s http://localhost:${ACTIVE_PORT}/api/v1/health | grep "blue\|green")

    if [ -z "${UP}" ]
        then
            sleep 30
            continue
    else
        break
    fi
done

if [ $cnt -eq 10 ]
then
    echo "서버가 정상적으로 구동되지 않았습니다."
    exit 1
fi

echo "> Docker Nginx: Change profile and port - Reload"
echo "set \$service_url http://spring-${ACTIVE_PROFILE}:${ACTIVE_PORT};" \
| tee ${ROOT_PATH}/data/nginx/conf.d/service-url.inc \
&& docker cp ${ROOT_PATH}/data/nginx/conf.d/service-url.inc nginx:/etc/nginx/conf.d/service-url.inc \
&& docker exec nginx nginx -s reload

echo "Docker : Idle $IDLE_PROFILE:$IDLE_PORT down"
docker-compose -p spring-${IDLE_PROFILE} ${ROOT_PATH}/docker-compose.${IDLE_PROFILE}.yml down

echo "$ACTIVE_PROFILE:$ACTIVE_PORT 배포가 정상적으로 완료되었습니다."
