# For documentation see https://jboss-container-images.github.io/openjdk/
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:1.24-2.1777575919@sha256:40a0e187612f9a8a660f6fce8f35ce59ab550eafa4a0695e7fe38dc1861eedc3

# Copy runnable jar to deployments
COPY target/*.jar /deployments/application.jar