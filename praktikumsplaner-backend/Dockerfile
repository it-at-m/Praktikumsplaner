# For documentation see https://jboss-container-images.github.io/openjdk/
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:1.23-6.1764562148@sha256:c9e38301c31ba579c9e766acb9e80415d925ea50a1b68b560cf7f3e75b6fa8dc

# Copy runnable jar to deployments
COPY target/*.jar /deployments/application.jar