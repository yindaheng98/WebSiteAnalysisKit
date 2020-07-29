FROM gradle:jdk8
COPY ./ /app
WORKDIR /app
RUN chmod +x ./gradlew && ./gradlew war

FROM jetty:9-jre8-alpine

COPY --from=0 /app/build/libs/WebSiteAnalysisKit.war /var/lib/jetty/webapps/WebSiteAnalysisKit.war