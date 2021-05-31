FROM azul/zulu-openjdk:11.0.11
COPY target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar"]