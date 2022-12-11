FROM openjdk:18

ADD target/event-worker-*.jar event-worker.jar
RUN sh -c 'touch /event-worker'
EXPOSE 8080
ENV JAVA_OPTS=""
CMD ["java", "-jar", "/event-worker.jar", "--spring.profiles.active=prod"]