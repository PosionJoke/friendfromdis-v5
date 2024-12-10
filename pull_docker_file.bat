@echo off

REM Set variables
set VERSION_NUMBER=%1

REM Build the Docker image
docker pull posionjoke/radio_bot_img:1.1.%VERSION_NUMBER%

REM Tag the Docker image for the registry
docker run --net=host --name radio_bot_img_v1.1.%VERSION_NUMBER% -d -p 8091:8091 posionjoke/radio_bot_img:1.1.%VERSION_NUMBER%
