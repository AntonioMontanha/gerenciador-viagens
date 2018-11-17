# Executando a Aplicação

## Instalação

Baixe e instale o Docker:
https://www.docker.com/

## Execute os comandos abaixo

Buildar a imagem do Gerenciador de Viagens:

docker build -t gerenciador-viagens .

Executar o container do Gerenciador de Viagens:

docker run -tid -p 8089:8089 --name="gerenciador-viagens" -v "${pwd}":/data/gerenciador-viagens gerenciador-viagens

Atenção: Caso esteja em um computador com SO Windows, substitua "${pwd}" pelo caminho onde está a o repositório do Gerenciador de Viagens, veja https://rominirani.com/docker-on-windows-mounting-host-directories-d96f3f056a2c

## Visualização dos Logs da Aplicação

Para visualizar os últimos logs da aplicação, basta executar o comando abaixo:

docker logs gerenciador-viagens
