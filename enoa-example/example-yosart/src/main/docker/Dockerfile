
FROM java:8-jre-alpine
COPY target/dist/example-yosart.tar.gz /tmp/example.tar.gz

WORKDIR /opt/example

RUN mkdir -p /opt && \
    tar -zxvf /tmp/example.tar.gz -C /opt && \
    mv /opt/example-yosart/* /opt/example && \
    rm -rf /opt/example-yosart && \
    rm -rf /tmp/example.tar.gz

EXPOSE 9102

ENTRYPOINT ["bin/entrypoint.sh", "start"]
