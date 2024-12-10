# Use OpenJDK 16 as the base image
FROM openjdk:21

# Add my jar file to docker image
ADD ./target/FriendFromDis-0.0.1-SNAPSHOT.jar .

# Define by witch port I can speack to my contaner
EXPOSE 9997
EXPOSE 5432

# witch command will be run after image run
#CMD java -jar discord_bot-beep-boop_0.0.1.jar
CMD java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5006 FriendFromDis-0.0.1-SNAPSHOT.jar
