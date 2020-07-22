FROM docker-sco-local.artifact.swissre.com/sco-baseimages/sr-azul-zulu-openjdk-11:latest

# Might be required for debugging
#RUN apt-get update && apt-get install -y net-tools

# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /zb-java-ms-template.jar

EXPOSE 5000
EXPOSE 6000

ENTRYPOINT java -jar /zb-java-ms-template.jar
