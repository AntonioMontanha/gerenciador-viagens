FROM ubuntu:18.04

RUN apt-get update

COPY . /data/gerenciador-viagens
WORKDIR /data/gerenciador-viagens

RUN chmod +x /data/gerenciador-viagens/start-app.sh
RUN ls -la /data/gerenciador-viagens

RUN apt-get install curl -y
RUN apt-get install software-properties-common -y
RUN add-apt-repository ppa:webupd8team/java
RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections
RUN apt-get install oracle-java8-installer -y
RUN apt-get install maven -y

EXPOSE 8089

RUN ls -la /data/gerenciador-viagens

CMD [ "bash", "/data/gerenciador-viagens/start-app.sh" ]
