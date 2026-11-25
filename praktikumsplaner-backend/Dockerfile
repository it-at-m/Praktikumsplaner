# For documentation see https://jboss-container-images.github.io/openjdk/
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:1.24-5.1795165349@sha256:e4190dfd1088220a0e3a63713e0cf9d5e42c277d2a06583db477b4baa60d4693

# Copy runnable jar to deployments
COPY target/*.jar /deployments/application.jar