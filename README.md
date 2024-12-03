
# SP_SpringBoot_AWS_Desafio_03

## 📝 Descrição

Este projeto é uma API RESTful desenvolvida em Java com Spring Boot, hospedada na AWS utilizando EC2. Ele foi projetado para gerenciar interações entre microserviços utilizando Docker, OpenFeign e HATEOAS. A aplicação é composta por múltiplos microserviços, implementando operações CRUD. A comunicação entre os serviços é feita de forma eficiente e escalável.

Vídeo de demonstração: [Assista no YouTube](https://youtu.be/HgHTWBvyTgg)

## 📑 Índice

- [Funcionalidades](#funcionalidades)
- [Pré-requisitos](#pré-requisitos)
- [Como Rodar o Projeto](#como-rodar-o-projeto)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Testes](#testes)

## 🚀 Funcionalidades

- **Microserviços Dockerizados:** Todos os microserviços são containerizados utilizando Docker para facilitar o deployment e a escalabilidade.
- **HATEOAS:** A API segue o estilo HATEOAS para fornecer links navegáveis dentro das respostas das APIs.
- **OpenFeign:** Utilizado para fazer chamadas HTTP entre microserviços de forma simplificada.
- **Testes Unitários e de Integração:** Implementação de testes para garantir a qualidade e robustez do sistema.

## 🧰 Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

- [Java JDK 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [MySQL ou PostgreSQL](https://dev.mysql.com/downloads/installer/): Um banco de dados em funcionamento e configurado.
- [Maven](https://maven.apache.org/install.html): Para construir e gerenciar as dependências do projeto.
- [Docker](https://www.docker.com/): Para configurar o banco de dados e outros serviços em containers.
- [AWS EC2](https://aws.amazon.com/ec2/): Para o ambiente de produção.

## 🚀 Como Rodar o Projeto

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/VitorSAlb/SP_SpringBoot_AWS_Desafio_03
   cd SP_SpringBoot_AWS_Desafio_03
   ```

2. **Compile os microserviços com Maven:**

   Execute os seguintes comandos nas pastas de cada microserviço:

   ```bash
   mvn clean package
   ```

   Repita o comando nas pastas de todos os microserviços que fazem parte do projeto.

3. **Suba os containers com Docker Compose:**

   Após compilar os microserviços, execute o Docker Compose na pasta principal para iniciar todos os containers necessários:

   ```bash
   docker-compose up -d
   ```

4. **Acesse o microserviço do cliente:**

   Acesse o microserviço que será responsável pela interação com o front-end (client):

   ```bash
   cd client
   mvn clean package
   mvn spring-boot:run
   ```

5. **Verifique os logs dos containers:**

   Para monitorar a execução dos containers Docker:

   ```bash
   docker-compose logs -f
   ```

## 📨 Postman

### Para enviar a mensagem:

- Endpoint:  
  `http://localhost:8081/api/v1/message`

- **JSON de exemplo:**

   ```json
   {
      "title": "Teste",
      "message": "corpo da mensagem"
   }
   ```

## 📂 Estrutura de Pastas

### **Entidades**

- **Message:** Representa o corpo da mensagem.

### **Serviços**

- **MessageService:** Responsável pela lógica de negócios para salvar mensagens no banco de dados.

### **Repositórios**

- **MessageRepository:** Interface responsável pela persistência dos dados da mensagem.

### **Controladores**

- **MessageController:** Expõe os endpoints para enviar mensagens.

### **DTOs**

- **MessageDTO:** Representação dos dados da mensagem e seus campos.

### **Configurações**

- **DockerConfig:** Configurações do Docker para rodar a aplicação e serviços relacionados.

## 🔧 Testes

Os testes são divididos entre testes unitários e de integração:

- **Testes Unitários:** Garantem que cada unidade do sistema (métodos, serviços) funcione isoladamente.
- **Testes de Integração:** Garantem que a comunicação entre os microserviços e com o banco de dados esteja funcionando corretamente.

Para rodar os testes:

```bash
mvn test
```

## 📦 Docker

A aplicação foi containerizada com Docker. Para rodar os microserviços em containers:

1. **Suba os containers com Docker Compose:**

   ```bash
   docker-compose up -d
   ```

2. **Verifique os logs dos containers:**

   ```bash
   docker-compose logs -f
   ```