FROM eclipse-temurin:17
ADD target/money_transfer_service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5500
ENTRYPOINT ["java","-jar","/app.jar"]