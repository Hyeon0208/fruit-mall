FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Dspring.config.location=classpath:/application.properties,classpath:/application-real-db.properties,classpath:/application-oauth.properties,classpath:/application-oper.properties", "-jar", "app.jar"]
