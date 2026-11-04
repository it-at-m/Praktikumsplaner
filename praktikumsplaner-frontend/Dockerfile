# For documentation see https://github.com/sclorg/nginx-container
FROM registry.access.redhat.com/ubi10/nginx-126:10.2-1793809023@sha256:939acec66a669f2a85c6d03a259263c214a74c33bfba321aded7a59762cb7ce2

# Copy built web application
COPY dist .

# Copy custom nginx configurations
COPY docker/nginx/*.conf "${NGINX_DEFAULT_CONF_PATH}"

# Start the web server
CMD ["nginx", "-g", "daemon off;"]