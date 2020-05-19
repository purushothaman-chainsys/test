FROM java:8-jdk-alpine

COPY ./lib/ /usr/app

WORKDIR	/usr/app

ENTRYPOINT ["java","-cp", "lib/*","com.chainsys.microservices.Application"]
