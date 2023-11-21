FROM ubuntu:18.04

RUN apt-get update

COPY . /data/gerenciador-viagens
WORKDIR /data/gerenciador-viagens

RUN chmod +x /data/gerenciador-viagens/start-app.sh
RUN ls -la /data/gerenciador-viagens

RUN apt-get install curl -y
RUN apt-get install software-properties-common -y
RUN apt-get install openjdk-8-jdk -y  # Substituído a instalação do Oracle JDK pelo OpenJDK
RUN apt-get install maven -y

EXPOSE 8089

RUN ls -la /data/gerenciador-viagens

CMD [ "bash", "/data/gerenciador-viagens/start-app.sh" ]
