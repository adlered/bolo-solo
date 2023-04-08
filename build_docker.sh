#!/bin/bash

#
# 优化docker编译，只需要添加需要的文件
#

mkdir -p /tmp/bolo-solo

cp -rf ./Dockerfile ./gulpfile.js ./manifest.json ./package.json ./pom.xml src /tmp/bolo-solo

cp -rf ./settings.xml /tmp/bolo-solo

cd /tmp/bolo-solo && sudo docker build -t "zeekling/bolo" .
