FROM openjdk:18.0-jdk-oracle
WORKDIR /agency
COPY agency-web/target/*.jar agency.jar
CMD ["java", "-jar", "agency.jar"]