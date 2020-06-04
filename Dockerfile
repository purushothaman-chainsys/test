FROM openjdk:11

COPY ./CS_Devops /usr/app

WORKDIR /usr/app

ENTRYPOINT ["java","-jar","/usr/app/apm/target/apm-1.0.0-SNAPSHOT.jar"]

