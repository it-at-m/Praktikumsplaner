# For documentation see https://jboss-container-images.github.io/openjdk/
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:1.24-3.1790283193@sha256:45af2087488812260f0cb6c744fadfe0b4e16c6f46232a05a57d7d0ddf1aa55d

# Copy runnable jar to deployments
COPY target/*.jar /deployments/application.jar