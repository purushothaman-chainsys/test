FROM java:8-jdk-alpine

COPY ./test/ /usr/app

WORKDIR	/usr/app

ENTRYPOINT ["java","-cp", "lib/*","com.chainsys.microservices.Application"]
