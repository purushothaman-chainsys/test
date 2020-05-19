FROM java:8-jdk-alpine

COPY /var/lib/jenkins/workspace/Docker_build_publish/lib/ /usr/app

WORKDIR	/usr/app

ENTRYPOINT ["java","-cp", "lib/*","com.chainsys.microservices.Application"]
