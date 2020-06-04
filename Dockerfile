FROM openjdk:11

COPY ./CS_Devops /usr/app

WORKDIR /usr/app/target/

ENTRYPOINT ["java","-jar","apm-1.0.0-SNAPSHOT.jar"]

