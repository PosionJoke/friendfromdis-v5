@echo off

REM Set variables
set VERSION_NUMBER=%1

REM Build the Docker image
docker build -f Dockerfile -t friend_from_dis_img_v1.1.%VERSION_NUMBER% .

REM Tag the Docker image for the registry
docker tag friend_from_dis_img_v1.1.%VERSION_NUMBER% posionjoke/friend_from_dis_img:1.1.%VERSION_NUMBER%

@REM REM Log in to the Docker registry
@REM docker login -u %USERNAME% -p %PASSWORD% %REGISTRY%

REM Push the Docker image to the registry
docker push posionjoke/friend_from_dis_img:1.1.%VERSION_NUMBER%


CREATE TABLE cars (
  soundName VARCHAR(255),
  soundData VARCHAR(255),
  addedBy INT
);
