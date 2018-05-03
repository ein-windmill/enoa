
FROM java:8-jre-alpine
COPY target/dist/example-yosart-simple.tar.gz /tmp/example.tar.gz

WORKDIR /opt/example

RUN mkdir -p /opt && \
    tar -zxvf /tmp/example.tar.gz -C /opt && \
    mv /opt/example-yosart-simple/* /opt/example && \
    rm -rf /opt/example-yosart-simple && \
    rm -rf /tmp/example.tar.gz

EXPOSE 9102

ENTRYPOINT ["bin/entrypoint.sh", "start"]
