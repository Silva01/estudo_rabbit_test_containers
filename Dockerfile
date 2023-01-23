FROM --platform=linux/amd64 openjdk:8-jdk
ADD build/libs/api-queue-test-rabbitmq-0.0.1-SNAPSHOT-plain.jar /opt/api-queue-test-rabbitmq-0.0.1-SNAPSHOT-plain.jar

RUN chmod -R 777 /opt

EXPOSE 8080

ENTRYPOINT java -Doracle.jdbc.timezoneAsRegion=false -XX:+PrintFlagsFinal $JAVA_OPTIONS -jar /opt/api-queue-test-rabbitmq-0.0.1-SNAPSHOT-plain.jar
