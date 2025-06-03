FROM openjdk:17-jdk-slim
WORKDIR /app
RUN addgroup --system challenge && adduser --system --ingroup challenge thferreira
COPY target/*.jar app.jar
EXPOSE 8080
USER thferreira
CMD ["java", "-jar", "app.jar"]