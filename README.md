# Playlist API - Documentação

A **Playlist API** tem como objetivo gerenciar playlists musicais de forma simples e eficiente, permitindo a criação, busca, exclusão e listagem de playlists e gêneros musicais.

---

## Funcionalidades

- **Criar uma playlist**  
  Registra uma nova playlist com nome, descrição e músicas.

- **Buscar todas as playlists**  
  Retorna a lista completa de playlists registradas.

- **Buscar uma playlist por nome**  
  Permite encontrar uma playlist específica pelo nome.

- **Deletar uma playlist**  
  Remove uma playlist existente com base no nome.

- **Listar gêneros musicais**  
  Retorna os gêneros musicais disponíveis para seleção.

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.0
- Spring Web
- Spring Security (JWT para autenticação)
- H2 Database (em memória)
- Spring Data JPA
- Swagger (SpringDoc OpenAPI 3)
- JUnit
- Mockito
- Arquitetura Clean Architecture

---

## Estrutura da Aplicação

A aplicação foi desenvolvida com base nos princípios da **Clean Architecture**, garantindo separação clara de responsabilidades:

- **Controllers (Adapters In)**  
  Responsáveis por receber requisições HTTP e encaminhar para os casos de uso.

- **Use Cases (Application Layer)**  
  Contêm a lógica de negócio da aplicação.

- **Gateways (Ports Out)**  
  Interfaces que conectam o domínio a serviços externos (banco de dados, autenticação, etc).

- **Adapters Out (Infraestrutura)**  
  Implementações das portas de saída, como repositórios, autenticação e outros adaptadores externos.

---

##  Executando a Aplicação

1. Clone este repositório:
   ```bash
   git clone https://github.com/anderson-lima92/playlist-api.git
   cd playlist-api


2. Compile e execute a aplicação:
   ```bash
   mvn clean install
   ./mvnw spring-boot:run


3. Após a inicialização, acesse a documentação Swagger:

    http://localhost:8080/swagger-ui/index.html

## Interface Web
Para utilizar a interface frontend da aplicação, siga as instruções disponíveis no repositório do projeto frontend:

https://github.com/anderson-lima92/playlist-front/blob/main/README.md
