# Task Management API

## Descrição

A Task Management API é uma aplicação desenvolvida em Java com Spring Framework para o gerenciamento de tarefas e quadros. O sistema permite a criação de quadros, gerenciamento de membros com diferentes papéis (proprietário, membro, visualizador) e atribuição de tarefas a cada quadro. A autenticação é baseada em JWT e a API conta com endpoints para cadastro de usuários, autenticação e recuperação de senha, além de envio de notificações por e-mail.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- JUnit 5
- Mockito
- Maven
- FreeMarker (Templates de E-mail)

## Funcionalidades

### Autenticação e Autorização

- **Autenticação JWT:** Login, verificação e manutenção de sessões de usuário.
- **Autorização com papéis:** Diferentes níveis de acesso com base no papel do usuário em cada quadro (OWNER, MEMBER, VIEWER).

### Gerenciamento de Quadros

- **Criação de Quadros:** O usuário pode criar quadros para organizar suas tarefas.
- **Gerenciamento de Membros:** Cada quadro pode ter membros com diferentes papéis e permissões.
- **Listagem de Quadros e Membros:** Retorna detalhes de todos os membros de um quadro, incluindo o ID, nome, dono, membros e suas funções.

### Gerenciamento de Tarefas

- **Criação e Atualização de Tarefas:** Tarefas podem ser criadas e atribuídas a quadros.
- **Atribuição de Membros a Tarefas:** Os membros de um quadro podem ser designados a tarefas.
- **Alteração de Status de Tarefas:** Atualizar o status das tarefas (ex.: `ToDo`, `In Progress`, `Done`).

### Recuperação de Senha

- **Esqueci minha Senha:** Gera um link único para redefinir a senha, enviado por e-mail.
- **Alteração de Senha:** Autenticação do usuário e alteração de senha utilizando JWT.

### Envio de E-mails

- **Notificação de Cadastro e Recuperação de Senha:** E-mails automáticos para confirmação de conta e recuperação de senha.
- **Agendamento de E-mails:** Lembretes automáticos de entrevistas e outros eventos importantes.

## Instalação e Execução

### Pré-requisitos

- Java 17
- Maven
- PostgreSQL

### Passos

1. Clone o repositório:
    ```bash
    git clone https://github.com/kacielriff/project-task-management.git
    ```

2. Configure as variáveis de ambiente
    ```bash
    -Dspring.datasource.url=
    -Dspring.datasource.username=
    -Dspring.datasource.password=
    -Djwt.secret=
    -Djwt.expiration=
    -Dspring.mail.host=
    -Dspring.mail.username=
    -Dspring.mail.password=
   ```
   
3. Rode o projeto

## Endpoints

### Autenticação (auth)

- **POST /auth/login:** Autenticar um usuário e retornar um token JWT.
- **POST /auth/register:** Registrar um novo usuário.
- **POST /auth/forgot-password:** Enviar e-mail de recuperação de senha.
- **POST /auth/recover-password:** Recuperar de senha.
- **PUT /auth/change-password:** Alterar a senha do usuário autenticado.
- **GET /auth/logged-user:** Recupera o usuário logado.

### Quadros (board)

- **POST /boards:** Criar um novo quadro.
- **GET /boards/{boardId}:** Obter detalhes de um quadro.
- **PUT /boards:** Edita um quadro.
- **DELETE /boards:** Exclui um quadro.
- **GET /boards/list-user-boards:** Listar os quadros que o usuário faz parte.