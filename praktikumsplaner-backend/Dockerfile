# For documentation see https://jboss-container-images.github.io/openjdk/
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:1.24-3.1791779558@sha256:856ee6542b8fdf09a065fd5439309fc3f3c821fb8efac48aa6191c93635dd907

# Copy runnable jar to deployments
COPY target/*.jar /deployments/application.jar