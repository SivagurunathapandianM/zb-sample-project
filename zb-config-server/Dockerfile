FROM docker-sco-local.artifact.swissre.com/sco-baseimages/sr-azul-zulu-openjdk-11:latest

ARG HTTP_PROXY=http://gate-zrh-os.swissre.com:8080
ARG HTTPS_PROXY=http://gate-zrh-os.swissre.com:8080
ARG NO_PROXY=localhost,127.0.0.1,.swissre.com,.sccloud.swissre.com,.corp.gwpnet.com

ARG http_proxy=$HTTP_PROXY
ARG https_proxy=$HTTPS_PROXY
ARG no_proxy=$NO_PROXY

USER root
RUN apt-get update \
  && apt-get install -y git
USER swissre

# Might be required for debugging
#RUN apt-get update && apt-get install -y net-tools

# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /zb-config-server.jar

EXPOSE 8888

ENTRYPOINT java -jar /zb-config-server.jar
