FROM fabric8/java-alpine-openjdk11-jre:1.8

RUN apk add tzdata
RUN rm -rf /var/cache/apk/*

ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager -XshowSettings:vm"

COPY target/lib/* /deployments/lib/
COPY target/*-runner.jar /deployments/app.jar

ENTRYPOINT [ "/deployments/run-java.sh" ]