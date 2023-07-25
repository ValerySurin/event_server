FROM opensearchproject/opensearch:latest

ARG pluginName="events-server-1.0.0.zip"
COPY /target/releases/$pluginName /tmp/$pluginName

RUN /usr/share/opensearch/bin/opensearch-plugin install --batch file:///tmp/$pluginName

