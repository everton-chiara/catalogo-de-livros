# 📚 Literalura

Literalura é uma aplicação Java com terminal interativo que permite consultar, buscar e registrar livros gratuitos da API Gutendex, armazenando autores e obras em um banco de dados relacional. O sistema foi desenvolvido com foco em boas práticas de arquitetura, desempenho e clareza de navegação via terminal.

---

## 🚀 Funcionalidades principais

- ✅ Buscar livros por título na API pública do Gutendex.
- ✅ Salvar dados de livros e autores no banco PostgreSQL.
- ✅ Listar livros registrados.
- ✅ Listar autores e suas obras.
- ✅ Filtrar autores vivos em determinado ano.
- ✅ Filtrar livros por idioma.
- ✅ Interface interativa pelo terminal.

---

## 🛠️ Tecnologias e frameworks utilizados:
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Jackson (para leitura de JSON)
- API Gutendex (https://gutendex.com/)

---

## ✅ Requisitos para executar a aplicação:
- Java 17+
- Maven
- PostgreSQL (instalado e rodando)

---

## 🎯 Configuração do Banco de Dados
Edite o arquivo ```src/main/resources/application.properties``` com seus dados de acesso:

```java
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
⚠️ Crie o banco ```literalura``` manualmente no PostgreSQL antes de executar o projeto.

---

## 📦 Como rodar a aplicação:
Clone o projeto:

```java
git clone https://github.com/everton-chiara/catalogo-de-livros.git
```
Navegue até o diretório:

```java
cd literalura
```
Rode a aplicação:

```java
mvn spring-boot:run
```
O menu interativo será exibido no terminal. Basta seguir as opções.

---

## 🙌 Autor
Desenvolvido por Everton Chiara como parte do projeto de aprimoramento Java com Spring Boot e consumo de API pública.





