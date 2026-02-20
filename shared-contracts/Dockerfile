FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY build/libs/*.jar app.jar

# Library image skeleton, primarily for CI validation.
CMD ["sh", "-c", "echo shared-contracts library image built; ls -l /app"]
