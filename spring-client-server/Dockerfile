# docker file
# 1. Start with a base image containing Java runtime
FROM adoptopenjdk/openjdk11
# 2. Add Author info
LABEL maintainer="sangkil.an@cj.net"
# Add a volume to /tmp
VOLUME /tmp
# Make port 8761 available to the world outside this container
EXPOSE 80
# The application's jar file
ARG JAR_FILE=build/libs/spring-client-server-0.0.1-SNAPSHOT.jar
# Add the application's jar to the container
COPY ${JAR_FILE} spring-client-server.jar

ARG ENVIRONMENT
ENV USE_PROFILE=${ENVIRONMENT}
# Run the jar file
ENTRYPOINT ["java","-Dspring.profiles.active=${USE_PROFILE}","-Djava.security.egd=file:/dev/./urandom","-jar","/spring-client-server.jar"]