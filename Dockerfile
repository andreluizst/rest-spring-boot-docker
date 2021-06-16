FROM maven:3.8.1-jdk-11-slim
LABEL maintaioner rest-spring-boot-docker
#WORKDIR /app
COPY "../../documentos/.m2" "/usr/share/maven/.m2"
#COPY pom.xml .
#RUN mvn dependency:go-offline
#COPY . /app
EXPOSE 8080
