FROM eclipse-temurin:17.0.9_9-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8070