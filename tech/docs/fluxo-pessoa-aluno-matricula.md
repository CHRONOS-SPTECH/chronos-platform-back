# Fluxo de pessoa, aluno e matrícula

## Regras implementadas

- Os vínculos são Público Externo, Provacionista, Membro e Membro Força Viva.
- `GET /pessoas` lista pessoas ativas com vínculo Público Externo.
- `GET /alunos` lista pessoas ativas com pelo menos uma matrícula em turma e vínculo Provacionista, Membro ou Membro Força Viva.
- A listagem de `/pessoas` retorna somente ID, nome, e-mail, telefone e vínculo; o CPF é mascarado, exibindo apenas os três últimos dígitos. A ficha detalhada continua retornando os dados completos.
- A listagem de `/alunos` retorna dados de contato e vínculo, datas relevantes, bolsa, foto, percentual de presença agregado nas turmas em andamento e todas as turmas em andamento vinculadas ao aluno. Sem chamadas registradas, o percentual é `0.0`; sem turmas em andamento, `turmas_atuais` é uma lista vazia.
- As listagens aceitam `page` (base zero), `size` (padrão 20, máximo 100) e `sort`. A ordenação inclui o ID da pessoa como critério de desempate.
- Inativação preenche `data_saida`, desativa o acesso de usuário vinculado e preserva matrículas e histórico. A rota antiga `DELETE /pessoas/{id}` foi mantida como alias para inativação. Reativar a pessoa não reativa automaticamente a conta de acesso; isso deve ser feito pelo fluxo de usuários.
- Biometria facial é criptografada e nunca é devolvida nos DTOs comuns de pessoa. A remoção da biometria não remove a foto de perfil.
- O detalhe por ID inclui endereço opcional, matrículas/turmas, presença por turma, histórico acadêmico e `possui_biometria`; o vetor biométrico nunca é exposto.
- O endereço pode ser enviado opcionalmente dentro do body de cadastro ou edição da pessoa. Sua gravação ocorre na mesma transação da pessoa.
- A matrícula em turma é identificada pela chave composta `id_turma + id_pessoa`, como definido no banco.

## Endpoints

| Método e rota | Uso |
| --- | --- |
| `POST /pessoas` | Cadastrar pessoa sem exigir biometria |
| `POST /pessoas/registro` | Cadastrar pessoa com imagem e vetor biométrico opcionais |
| `GET /pessoas?page=0&size=20` | Listar Público Externo ativo |
| `GET /alunos?page=0&size=20` | Listar alunos elegíveis com matrícula em turma |
| `GET /pessoas/{id}` | Buscar dados básicos por ID |
| `GET /pessoas/details/{id}` | Buscar ficha completa: dados da pessoa, vínculo, endereço, turmas, presença, histórico acadêmico e indicador de biometria |
| `PUT /pessoas/{id}` | Editar dados da pessoa |
| `PUT /pessoas/{id}/biometria` | Cadastrar ou substituir vetor biométrico; a imagem Base64 é opcional |
| `DELETE /pessoas/{id}/biometria` | Remover o vetor biométrico criptografado |
| `PATCH /pessoas/{id}/inativacao` | Inativar, registrando `data_saida` |
| `PATCH /pessoas/{id}/reativacao` | Reativar, limpando `data_saida` |
| `POST /matriculas-turma` | Criar matrícula, validando pessoa, turma, vínculo e duplicidade |
| `GET /matriculas-turma/{idTurma}/pessoas/{idPessoa}` | Consultar matrícula pela chave composta |
| `PUT /matriculas-turma/{idTurma}/pessoas/{idPessoa}` | Atualizar a data, mantendo a chave composta |
| `DELETE /matriculas-turma/{idTurma}/pessoas/{idPessoa}` | Remover matrícula em turma |

## Paginação

Exemplo: `GET /alunos?page=0&size=20&sort=nome,asc`. O tamanho solicitado acima de 100 é limitado a 100. A API retorna a estrutura `Page` do Spring Data (`content`, `totalElements`, `totalPages`, `number`, `size` e metadados de página).

## Cadastro com endereço

O DTO de cadastro aceita o objeto opcional `endereco`, sem `id_pessoa` (o backend associa o endereço à pessoa recém-criada):

```json
{
  "nome": "Ana Silva",
  "email": "ana@example.com",
  "telefone": "11999999999",
  "tipo_vinculo_id": 1,
  "endereco": {
    "cep": "07000000",
    "logradouro": "Rua Exemplo",
    "numero": "100",
    "complemento": "Apto 12",
    "bairro": "Centro",
    "cidade": "Guarulhos",
    "uf": "SP"
  }
}
```

O mesmo objeto é aceito em `POST /pessoas/registro` e em `PUT /pessoas/{id}`. Se omitido no cadastro, a pessoa é criada sem endereço; se omitido na edição, o endereço existente não é alterado.

## Resposta da ficha completa

`GET /pessoas/details/{id}` retorna os dados pessoais e acrescenta:

- `endereco`: objeto de endereço ou `null` quando a pessoa não tem endereço cadastrado.
- `possui_biometria`: booleano; o vetor biométrico criptografado não é exposto.
- `turmas`: lista de matrículas, com nome/status da turma, data da matrícula, chamadas registradas, presenças e percentual por turma.
- `total_chamadas`, `presencas` e `percentual_presenca`: resumo agregado das turmas vinculadas.
- `historico_academico`: matéria, nível, status e data de cada registro acadêmico.

## Compatibilidade

`GET /pessoas` passa de lista sem paginação para `Page` e restringe o conteúdo a Público Externo ativo. Clientes devem consumir `content` e usar os metadados para navegar. Para estudantes, usar `/alunos`.

Matrícula não possui ID simples no esquema SQL. O contrato antigo de consulta/edição/remoção por `/matriculas-turma/{id}` e o campo inexistente `id_matricula_turma` foram substituídos pela chave composta. Clientes devem guardar os IDs da turma e da pessoa.
