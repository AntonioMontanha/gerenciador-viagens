# gerenciador-viagens

1. Configurar Maven e Java (Variáveis de Ambiente)

2. Escolha um diretório e abra um Git Bash e Execute: git clone https://github.com/AntonioMontanha/gerenciador-viagens.git

3. Abra a aplicação na IDE de sua escolha

4. No diretorio do projeto execute mvn clean install

5. Acesse a pasta src/main/java abra a package com.montanha.gerenciador e a classe GerenciadorViagensMontanhaApplication

6. Clique com o botão direito e selecione Run as > Java Aplication

# Dockerized

docker build -t gerenciador-viagens .
docker run -tid -p 8089:8089 --name="gerenciador-viagens" -v "${PWD}":/data/gerenciador-viagens gerenciador-viagens
docker logs gerenciador-viagens
