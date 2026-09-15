# For documentation see https://jboss-container-images.github.io/openjdk/
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:1.24-3.1788862450@sha256:ff0a095f96c1823453008aef23b98868f3bf723fa0497d68c2fb9b1a27884064

# Copy runnable jar to deployments
COPY target/*.jar /deployments/application.jar