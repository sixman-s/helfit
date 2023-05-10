#!/usr/bin/env bash
find /home/ubuntu/build -maxdepth 2 \
  ! -wholename '/home/ubuntu/build' \
  ! -wholename '/home/ubuntu/build/data' \
  ! -wholename '/home/ubuntu/build/data/certbot' \
  -exec rm -rf {} +
