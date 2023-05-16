FROM adoptopenjdk/openjdk11:latest
EXPOSE 8081
ADD target/mba-docker.jar mba-docker.jar
ENTRYPOINT ["java","-jar","/mba-docker.jar"]