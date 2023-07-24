FROM openjdk:17.0
WORKDIR /insurance
COPY insurance-web/target/*.jar insurance.jar
CMD ["java", "-jar", "insurance.jar"]