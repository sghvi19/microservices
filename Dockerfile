FROM openjdk:17.0-jdk-oracle
WORKDIR /justice
COPY justice-web/target/*.jar justice.jar
CMD ["java", "-jar", "justice.jar"]