FROM openjdk:8
ADD target/camunda-bpm-engine.jar camunda-bpm-engine.jar
EXPOSE 9091
ENTRYPOINT ["java","-jar","camunda-bpm-engine.jar"]
