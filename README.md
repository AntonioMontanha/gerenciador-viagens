# Executando a Aplicação

## Instalação

Baixe e instale o Docker:
https://www.docker.com/

## Execute os comandos abaixo

Buildar a imagem do Gerenciador de Viagens:

docker build -t gerenciador-viagens .

Espelharemos o diretório onde está o repositório para dentro do container, por isso, se você usa Windows, precisará executar a atividade a seguir para permitir o uso de volumes:

https://rominirani.com/docker-on-windows-mounting-host-directories-d96f3f056a2c

Agora iremos rodar o container do Gerenciador de Viagens:

docker run -tid -p 8089:8089 --name="gerenciador-viagens" -v C:/Temp/projects/gerenciador-viagens:/data/gerenciador-viagens gerenciador-viagens

- O caminho a seguir é apenas um exemplo, substitua-o pelo caminho do diretório gerenciador-viagens: 
C:/Temp/projects/gerenciador-viagens

## Visualização dos Logs da Aplicação

Para visualizar os últimos logs da aplicação, basta executar o comando abaixo:

docker logs gerenciador-viagens
