# LiterAlura

> Catálogo de livros que consome a API Gutendex e persiste dados em PostgreSQL, desenvolvido em Java com Spring Boot.

## 🚀 Tecnologias

- **Backend**: Java 17 + Spring Boot 3.x
- **Banco de Dados**: PostgreSQL
- **API Externa**: [Gutendex](https://gutendex.com/) (livros gratuitos)
- **Dependências**:
  - Spring Data JPA
  - PostgreSQL Driver
  - Jackson (para parsing JSON)

## ⚙️ Configuração

1. **Pré-requisitos**:
   - Java 17+
   - PostgreSQL (crie um banco chamado `literalura`)
   - Maven (opcional)

2. **Clone e execute**:
   ```bash
   git clone https://github.com/seu-usuario/literalura.git
   cd literalura
   ./mvnw spring-boot:run
Configure o banco:
Edite src/main/resources/application.properties:

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=seu_user
spring.datasource.password=sua_senha
📋 Funcionalidades
Comando	Descrição	Exemplo de Uso
1 - Buscar livro	Consulta livros na API por título	Dom Casmurro
2 - Listar livros	Exibe todos os livros salvos no BD	-
3 - Listar autores	Mostra autores e seus livros	-
4 - Autores por ano	Filtra autores vivos em um ano específico	1800 → Jane Austen
5 - Livros por idioma	Busca por idioma (pt, en, es, fr)	pt → "Dom Casmurro"
🏗️ Estrutura do Projeto
text
src/
├── main/
│   ├── java/
│   │   └── com.literalura.demo/
│   │       ├── dto/            # DTOs da API
│   │       ├── model/          # Entidades (Autor, Livro)
│   │       ├── repository/     # Repositórios JPA
│   │       ├── service/        # Lógica de negócio
│   │       └── DemoApplication.java
│   └── resources/
│       └── application.properties


---

### ✨ Por que esta versão?
1. **Objetividade**: Sem enfeites, só o necessário para outro dev usar/contribuir.
2. **Foco técnico**: Detalha estrutura, configuração e fluxo de dados.
3. **Tabelas organizadas**: Facilita a consulta rápida de funcionalidades.
4. **Exemplo real**: Inclui um snippet do JSON da API para referência.
