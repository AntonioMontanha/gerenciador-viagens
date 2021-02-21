# API REST Gerenciador de Viagens

> Este é um repositório para aprender um pouco mais sobre APIs REST.
> 
> Nele existem vários problemas e más práticas para serem utilizadas 
> como exemplo do que não fazer.
> 
> --<cite>[Antônio Montanha](https://github.com/AntonioMontanha/gerenciador-viagens)</cite>

## [Curso Gratuito de Introdução aos Testes de API Rest](https://www.youtube.com/playlist?list=PLf8x7B3nFTl17WeEVj405tHlstiq1kNBX)

[Júlio de Lima](https://github.com/juliointest)

## Java Version 1.8

## Verificar se API está rodando
http://localhost:8089/api/v1/status

## Documentação Swagger
http://localhost:8089/api/swagger-ui.html

## [cURL](https://pt.wikipedia.org/wiki/CURL) - Client URL

### Verificação de Status da API
`$ curl -X GET http://localhost:8089/api/v1/status`

### Autenticação e geração de token

#### Admin

`$ curl -X POST http://localhost:8089/api/v1/auth -d '{"email":"admin@email.com", "senha": "654321"}' -H 'Content-Type: application/json'`


### Autenticação com retorno de cabeçalho

#### Usuário comum
`$ curl -X POST -is http://localhost:8089/api/v1/auth -d '{"email":"usuario@email.com", "senha": "123456"}' -H 'Content-Type: application/json'`

### Cadastro de uma viagem com o token de Admin
`$ curl -X POST -is http://localhost:8089/api/v1/viagens -d '{"acompanhante": "Isabelle", "dataPartida": "2021-02-23","dataRetorno": "2021-02-30","localDeDestino": "Manaus",  "regiao": "Norte"}' -H 'Content-Type: application/json' -H 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBlbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9BRE1JTiIsImNyZWF0ZWQiOjE2MTM4NzE5MTMxOTMsImV4cCI6MTYxMzk3MTkxMn0.0_CCQqok7D0Y2mdR39L9HAxKYkLPOgD_65-LvuRkM4jhFmB94-8_ju-1R8i5P2X9B7RGw7jFwyid-7sAxvM_rg'`

#### Resposta: 201 Criado

### Cadastro de uma viagem com o token de Usuário Comum
`$ curl -X POST -is http://localhost:8089/api/v1/viagens -d '{"acompanhante": "Isabelle", "dataPartida": "2021-02-23","dataRetorno": "2021-02-30","localDeDestino": "Manaus",  "regiao": "Norte"}' -H 'Content-Type: application/json' -H 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c3VhcmlvQGVtYWlsLmNvbSIsInJvbGUiOiJST0xFX1VTVUFSSU8iLCJjcmVhdGVkIjoxNjEzODczMzczMjA1LCJleHAiOjE2MTM5NzMzNzJ9.qaw8PxOA2PFbyMaEa7gHJ-yza_E1h5heL9LRNS506Ac8mIpc4KEjDSL4dwy964oqPNV3G42Xe4yRKE-7DEOCZw'`

#### Resposta: 403 Acesso negado - Forbidden / Proibido

### Cadastro de uma viagem com o token inválido
`$ curl -X POST -is http://localhost:8089/api/v1/viagens -d '{"acompanhante": "Isabelle", "dataPartida": "2021-02-23","dataRetorno": "2021-02-30","localDeDestino": "Manaus",  "regiao": "Norte"}' -H 'Content-Type: application/json' -H 'Authorization: tokeninválido'`

#### Resposta: 401 Acesso negado - Unauthorized / Não autorizado
