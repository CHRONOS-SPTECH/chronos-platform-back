# Chronos

Sistema de gestão acadêmica, cultural e de voluntariado da **Nova Acrópole – Filial Guarulhos**, desenvolvido pela Kairoz Tech (Grupo 8 – São Paulo Tech School, Análise e Desenvolvimento de Sistemas).

O Chronos reúne em um só lugar o cadastro de alunos, as turmas, o cronograma de aulas, a chamada digital, os eventos e uma dashboard com indicadores da comunidade. O acesso é feito por perfil (Administrador, Secretária e Instrutor).

O sistema é formado por três repositórios:

| Repositório | Conteúdo |
| --- | --- |
| `chronos-platform-back` | API (Java 21 e Spring Boot) e script do banco de dados, na pasta `database` |
| `chronos-platform-web` | Aplicação web (React) |
| `chronos-microservice-email` | Serviço de envio de e-mails (opcional) |

---

## Manual de Instalação

### 1. Pré-requisitos

- [JDK 21](https://adoptium.net/)
- [Node.js 20](https://nodejs.org/) ou superior
- [MySQL 8](https://dev.mysql.com/downloads/mysql/)
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/) (apenas para o serviço de e-mail)

### 2. Baixar o código

```bash
git clone https://github.com/CHRONOS-SPTECH/chronos-platform-back.git
git clone https://github.com/CHRONOS-SPTECH/chronos-platform-web.git
git clone https://github.com/CHRONOS-SPTECH/chronos-microservice-email.git
```

### 3. Criar o banco de dados

Execute o script, que cria o banco `chronos_db` com as tabelas e os dados de configuração:

```bash
mysql -u root -p < chronos-platform-back/database/chronos_db.sql
```

No Windows (PowerShell): `Get-Content .\chronos-platform-back\database\chronos_db.sql | mysql -u root -p`. Também é possível executar o arquivo pelo MySQL Workbench.

### 4. Iniciar a API

Defina as variáveis de ambiente e inicie a API na pasta `chronos-platform-back/tech`:

| Variável | Descrição |
| --- | --- |
| `DB_USERNAME` / `DB_PASSWORD` | Usuário e senha do MySQL |
| `JWT_SECRET` | Chave dos tokens de login, com pelo menos 32 caracteres aleatórios |
| `CRYPTO_ENCRYPTION_KEY` | Chave de criptografia da biometria. Defina antes de cadastrar dados reais e não altere depois |

Windows (PowerShell):

```powershell
cd chronos-platform-back\tech
$env:DB_USERNAME = "usuario"; $env:DB_PASSWORD = "senha"; $env:JWT_SECRET = "chave-com-pelo-menos-32-caracteres"
.\mvnw.cmd spring-boot:run
```

Linux ou macOS:

```bash
cd chronos-platform-back/tech
export DB_USERNAME=usuario DB_PASSWORD=senha JWT_SECRET=chave-com-pelo-menos-32-caracteres
./mvnw spring-boot:run
```

A API fica disponível em `http://localhost:8080`, com a documentação em `http://localhost:8080/swagger-ui.html`.

### 5. Criar o primeiro usuário

O banco começa sem usuários. Crie o Administrador pelo Swagger, em `POST /auth/register`:

```json
{
  "pessoa": {
    "nome": "Nome do Administrador",
    "email": "admin@instituicao.org",
    "telefone": "11999990000",
    "tipo_vinculo_id": 4
  },
  "email_login": "admin@instituicao.org",
  "senha": "SenhaForte123"
}
```

Os demais usuários são cadastrados pelo Administrador, dentro do sistema.

### 6. Iniciar a aplicação web

Na pasta `chronos-platform-web`:

```bash
cp .env.example .env
npm install
npm run dev
```

No Windows, use `Copy-Item .env.example .env` no lugar do `cp`. Acesse `http://localhost:5173`. Se a API estiver em outro endereço, ajuste `VITE_API_TARGET` no arquivo `.env`.

Para produção, `npm run build` gera os arquivos na pasta `dist`. Configure o servidor web para encaminhar as requisições `/api/*` para a API, sem o prefixo `/api`.

### 7. Serviço de e-mail (opcional)

Nesta versão o serviço de e-mail não é acionado pela API. Para executá-lo, na pasta `chronos-microservice-email`:

```bash
docker compose up -d
cd email
./mvnw spring-boot:run
```

Antes de iniciar, informe o usuário e a senha do MySQL e os dados do servidor SMTP no arquivo `email/src/main/resources/application.properties`. Como a API já usa a porta 8080, defina a variável `SERVER_PORT=8081` para o serviço de e-mail.

---

## Manual do Usuário

### Acesso

1. Abra o endereço do Chronos no navegador, no computador ou no celular.
2. Informe e-mail e senha e clique em **Entrar no Sistema**.
3. Escolha o perfil com que deseja trabalhar. Quem tem mais de uma função vê um cartão para cada perfil.

A sessão expira após 24 horas. A recuperação de senha ainda não está ativa: peça ao Administrador para redefini-la. O botão azul à direita da tela abre a tradução para Libras (VLibras).

### Instrutor

- **Fazer a chamada:** clique em **Marcar Presença** na página inicial ou em **Chamada do dia** no menu. Confira a turma e o horário, ligue o botão dos alunos presentes e clique em **Salvar Chamada**.
- **Minha Agenda:** mostra as aulas que você conduz.
- A página inicial também exibe o cronograma do dia e os próximos eventos.

### Secretária

- **Página inicial:** atalhos para novo aluno, nova turma e cronograma, além das aulas do dia e das pendências.
- **Alunos:** cadastre em **+ Novo Aluno**. Nome, e-mail, telefone e vínculo são obrigatórios; o CPF tem 11 números, sem pontos ou traço. Use os ícones da coluna **Ações** para ver, editar ou excluir.
- **Turmas:** crie em **+ Nova Turma** e use **Ver alunos** para consultar os matriculados.
- **Eventos:** cadastre título, data, horário e local; os eventos podem ser filtrados por situação.
- **Cronograma de Aulas:** calendário por mês ou semana, com filtros por turma e instrutor. O botão **Pendências** lista as aulas sem data, que precisam ser agendadas.
- **Gestão de Aulas:** lista todas as aulas. O botão **Importar Carga de Aulas** recebe uma planilha Excel (formato abaixo).
- **Dashboard:** indicadores de membros, instrutores, gênero e faixa etária.

**Planilha de importação:** as duas primeiras linhas são de cabeçalho e as aulas começam na linha 3. Cada dia ocupa um bloco de 6 colunas a partir da coluna B, nesta ordem: data (dd/mm/aaaa), horário (hh:mm), turma, tema da aula, professor e uma coluna vazia. Turma, tema e professor devem estar escritos exatamente como no cadastro. Aulas com conflito de horário entram sem data e aparecem em **Pendências**.

### Administrador

Tem acesso à dashboard, a alunos, turmas e eventos e a **Controle de Acesso > Usuários**, onde cadastra, edita e exclui os usuários e define o perfil de cada um.

---

## Problemas comuns

| Problema | Solução |
| --- | --- |
| A API não inicia por falta de `DB_USERNAME`, `DB_PASSWORD` ou `JWT_SECRET` | Defina as variáveis no mesmo terminal em que executa o `mvnw` |
| Erro de tabela ou coluna ausente ao iniciar a API | Crie o banco novamente com o script `chronos_db.sql` |
| Porta 8080 em uso | Encerre o outro programa ou inicie o serviço de e-mail com `SERVER_PORT=8081` |
| Login falha com erro de rede | Verifique se a API está rodando e se `VITE_API_TARGET` aponta para ela |
| Nenhum perfil aparece após o login | Peça ao Administrador para atribuir um perfil ao usuário |
| A importação recusa linhas | Confira os nomes de turma, tema e professor e os formatos de data e hora |