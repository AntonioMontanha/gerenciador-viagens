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

## AUTENTICAÇÃO

### Autenticação e geração de token

#### Admin
```
$ curl -X POST http://localhost:8089/api/v1/auth -d '{"email":"admin@email.com", "senha": "654321"}' -H 'Content-Type: application/json'
```

### Autenticação com retorno de cabeçalho

#### Usuário comum
```
$ curl -X POST -is http://localhost:8089/api/v1/auth -d '{"email":"usuario@email.com", "senha": "123456"}' -H 'Content-Type: application/json'
```

## CADASTRO DE VIAGENS

### Cadastro de uma viagem com o token de Admin Autorizado
```
$ curl -X POST -is http://localhost:8089/api/v1/viagens -d '{"acompanhante": "Isabelle", "dataPartida": "2021-02-23","dataRetorno": "2021-02-30","localDeDestino": "Manaus",  "regiao": "Norte"}' -H 'Content-Type: application/json' -H 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBlbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9BRE1JTiIsImNyZWF0ZWQiOjE2MTM4NzE5MTMxOTMsImV4cCI6MTYxMzk3MTkxMn0.0_CCQqok7D0Y2mdR39L9HAxKYkLPOgD_65-LvuRkM4jhFmB94-8_ju-1R8i5P2X9B7RGw7jFwyid-7sAxvM_rg'
```
#### Resposta: 201 Criado

### Cadastro de uma viagem com o token de Usuário Comum Não Autorizado
```
$ curl -X POST -is http://localhost:8089/api/v1/viagens -d '{"acompanhante": "Isabelle", "dataPartida": "2021-02-23","dataRetorno": "2021-02-30","localDeDestino": "Manaus",  "regiao": "Norte"}' -H 'Content-Type: application/json' -H 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c3VhcmlvQGVtYWlsLmNvbSIsInJvbGUiOiJST0xFX1VTVUFSSU8iLCJjcmVhdGVkIjoxNjEzODczMzczMjA1LCJleHAiOjE2MTM5NzMzNzJ9.qaw8PxOA2PFbyMaEa7gHJ-yza_E1h5heL9LRNS506Ac8mIpc4KEjDSL4dwy964oqPNV3G42Xe4yRKE-7DEOCZw'
```
#### Resposta: 403 Acesso negado - Forbidden / Proibido

### Cadastro de uma viagem com o token inválido
```
$ curl -X POST -is http://localhost:8089/api/v1/viagens -d '{"acompanhante": "Isabelle", "dataPartida": "2021-02-23","dataRetorno": "2021-02-30","localDeDestino": "Manaus",  "regiao": "Norte"}' -H 'Content-Type: application/json' -H 'Authorization: tokeninválido'
```
#### Resposta: 401 Acesso negado - Unauthorized / Não autorizado

## CONSULTA DE VIAGENS

### Consultando viagens com Token de Usuário Comum
```
$ curl -X GET -is http://localhost:8089/api/v1/viagens -H 'Content-Type:application/json' -H 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c3VhcmlvQGVtYWlsLmNvbSIsInJvbGUiOiJST0xFX1VTVUFSSU8iLCJjcmVhdGVkIjoxNjEzODczMzczMjA1LCJleHAiOjE2MTM5NzMzNzJ9.qaw8PxOA2PFbyMaEa7gHJ-yza_E1h5heL9LRNS506Ac8mIpc4KEjDSL4dwy964oqPNV3G42Xe4yRKE-7DEOCZw'
```
#### Resposta: 200 Ok

### Consultando uma viagem específica com Token de Usuário Comum e _parâmetro path_
```
$ curl -X GET -is http://localhost:8089/api/v1/viagens/1 -H 'Content-Type:application/json' -H 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c3VhcmlvQGVtYWlsLmNvbSIsInJvbGUiOiJST0xFX1VTVUFSSU8iLCJjcmVhdGVkIjoxNjEzODczMzczMjA1LCJleHAiOjE2MTM5NzMzNzJ9.qaw8PxOA2PFbyMaEa7gHJ-yza_E1h5heL9LRNS506Ac8mIpc4KEjDSL4dwy964oqPNV3G42Xe4yRKE-7DEOCZw'
```
#### Resposta: 200 Ok

### Consultando viagens com Token de Usuário Comum de uma Região específica com _parâmetro query_
```
$ curl -X GET -is http://localhost:8089/api/v1/viagens?regiao=Norte -H 'Content-Type:application/json' -H 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c3VhcmlvQGVtYWlsLmNvbSIsInJvbGUiOiJST0xFX1VTVUFSSU8iLCJjcmVhdGVkIjoxNjEzODczMzczMjA1LCJleHAiOjE2MTM5NzMzNzJ9.qaw8PxOA2PFbyMaEa7gHJ-yza_E1h5heL9LRNS506Ac8mIpc4KEjDSL4dwy964oqPNV3G42Xe4yRKE-7DEOCZw'
```
#### Resposta: 200 Ok


### Consultando viagens com Token de Usuário Comum de uma Região específica com _parâmetro query não existente_
```
$ curl -X GET -is http://localhost:8089/api/v1/viagens?regiao=Sul -H 'Content-Type:application/json' -H 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c3VhcmlvQGVtYWlsLmNvbSIsInJvbGUiOiJST0xFX1VTVUFSSU8iLCJjcmVhdGVkIjoxNjEzODczMzczMjA1LCJleHAiOjE2MTM5NzMzNzJ9.qaw8PxOA2PFbyMaEa7gHJ-yza_E1h5heL9LRNS506Ac8mIpc4KEjDSL4dwy964oqPNV3G42Xe4yRKE-7DEOCZw'
```
#### Resposta: 500 Erro interno do servidor


## [7 Popular Unit Test Naming Conventions](https://dzone.com/articles/7-popular-unit-test-naming)

### Given When Then
**Given_Preconditions_When_StateUnderTest_Then_ExpectedBehavior**: This approach is based on naming convention developed as part of Behavior-Driven Development (BDD). The idea is to break down the tests into three part such that one could come up with preconditions, state under test and expected behavior to be written in above format. Following is how tests in first example would read like if named using this technique:

- Given_UserIsAuthenticated_When_InvalidAccountNumberIsUsedToWithdrawMoney_Then_TransactionsWillFail    