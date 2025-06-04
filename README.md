# Documentação da Aplicação

A API tem como objetivo gerenciar playlists musicais. As funcionalidades disponíveis incluem:

- **Criar uma playlist**: Permite o registro de uma nova playlist com nome, descrição e músicas.
- **Buscar todas as playlists**: Lista todas as playlists registradas.
- **Buscar uma playlist por nome**: Permite a busca de uma playlist pelo nome.
- **Deletar uma playlist**: Remove uma playlist existente com base no nome.
- **Listar gêneros musicais**: Retorna a lista de gêneros disponíveis para preenchimento de músicas.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.0
- Spring Web
- Spring Security com autenticação via JWT
- Banco de dados H2 (em memória)
- Spring Data JPA
- Swagger (SpringDoc OpenAPI 3)
- JUnit
- Mockito
- Arquitetura Clean Architecture

## Estrutura da Aplicação

A aplicação foi desenvolvida seguindo os princípios da Clean Architecture, separando claramente as responsabilidades em:

- **Controllers (Adapters In)**: Recebem as requisições HTTP.
- **Use Cases (Application)**: Contêm a lógica de negócio da aplicação.
- **Gateways (Ports Out)**: Interfaces que conectam o domínio com implementações externas.
- **Infraestrutura (Adapters Out)**: Implementações de acesso a banco de dados, segurança, etc.

## Executando a Aplicação

Para executar a aplicação, navegue até a pasta do projeto e utilize o comando:

```bash
./mvnw spring-boot:run

Após a inicialização, siga as instruções para acessar a inerface, caso ainda não tenha feito.
https://github.com/anderson-lima92/playlist-front/blob/main/README.md