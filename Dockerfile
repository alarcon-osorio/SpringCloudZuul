FROM openjdk:11
WORKDIR /app
COPY target/ZuulGatewayServer-1.0-SNAPSHOT.jar ZuulGatewayServer-1.0-SNAPSHOT.jar
EXPOSE 8080
CMD [ "java", "-jar", "ZuulGatewayServer-1.0-SNAPSHOT.jar" ]