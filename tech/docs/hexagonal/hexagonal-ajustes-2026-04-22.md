# Ajustes Hexagonais - 2026-04-22

## Objetivo

Consolidar a migracao para arquitetura hexagonal com foco em consistencia de contratos de entrada (`UseCase`), padronizacao de DTOs e fechamento do fluxo completo do modulo `Usuario`.

## Alteracoes Realizadas

### 1) Padronizacao de DTO e contrato de TipoParticipacao

- Renomeado o DTO de resposta de `TipoParticipacaoRespondeDTO` para `TipoParticipacaoResponseDTO`.
- Criado novo arquivo de DTO:
  - `application/dto/response/TipoParticipacaoResponseDTO`
- Atualizados os pontos de uso para o novo DTO:
  - `application/mapper/TipoParticipacaoMapper`
  - `application/port/in/TipoParticipacaoUseCase`
  - `application/service/TipoParticipacaoService`
  - `infrastructure/web/TipoParticipacaoController`
- Removido DTO antigo:
  - `application/dto/response/TipoParticipacaoRespondeDTO`

Beneficio:
- Elimina inconsistencias de nomenclatura em contratos de API e melhora legibilidade/manutenibilidade.

### 2) Padronizacao de operacao delete em TipoParticipacao

- Ajustado nome do metodo da porta de entrada e implementacao:
  - `deletTipoParticipacao` -> `deleteTipoParticipacao`
- Camadas atualizadas:
  - `TipoParticipacaoUseCase`
  - `TipoParticipacaoService`
  - `TipoParticipacaoController`

Beneficio:
- Contrato mais claro e consistente com os demais metodos CRUD.

### 3) Novos modulos hexagonais completos (DTO + Mapper + Portas + Adapters)

Foram criados modulos completos para classes de dominio que ainda nao tinham camadas hexagonais:

- `Materia`
- `TemaAula`
- `HistoricoAcademico`
- `MatriculaTurma`
- `GestaoSecretaria`
- `Participacao` (normalizacao do nome da classe, removendo acento)

Para cada modulo acima foram adicionados:

- DTO de request e response em `application/dto`
- Mapper MapStruct em `application/mapper`
- Porta de entrada `UseCase` em `application/port/in`
- Service de aplicacao em `application/service`
- Porta de saida de repositorio em `domain/port`
- Adapter JPA + Spring Data repository em `infrastructure/adapter/out/persistence`
- Controller REST em `infrastructure/web`

Tambem houve ajuste nas classes de dominio para entidades faltantes:

- `GestaoSecretaria`, `HistoricoAcademico` e `MatriculaTurma` passaram a ser entidades JPA com `@Entity`, `@Table`, `@Id` e relacionamentos.
- Classe `Participação` foi substituida por `Participacao` para padronizar nomenclatura e evitar problemas de manutencao/encoding.

### 4) Usuario tratado como autenticacao (Spring Security + JWT)

Antes:
- `Usuario` nao estava integrado ao fluxo de autenticacao.
- Nao havia camada JWT nem filtro de seguranca stateless.

Depois:
- Criado fluxo de autenticacao por porta de entrada:
  - `AuthUseCase`
  - `AuthService`
  - `AuthController` (`/auth/register` e `/auth/login`)
- Adicionada infraestrutura de seguranca:
  - `SecurityConfig` com sessao stateless, `PasswordEncoder`, `AuthenticationManager` e regras de autorizacao
  - `JwtService` para emissao/validacao de token
  - `JwtAuthenticationFilter` para autenticacao por header `Bearer`
  - `CustomUserDetailsService` + `CustomUserPrincipal`
- `UsuarioRepository` recebeu busca por e-mail (`findByEmailLogin`) para login.
- `application.properties` recebeu propriedades JWT (`security.jwt.secret`, `security.jwt.expiration-ms`).
- `pom.xml` recebeu dependencias `jjwt` para token.

Beneficio:
- `Usuario` passa a ser tratado como identidade/autenticacao, nao apenas como CRUD.
- API preparada para proteger endpoints via JWT, mantendo arquitetura hexagonal.

## Impacto Arquitetural

- Reforco do contrato por portas de entrada (`application.port.in`) com cobertura de modulos de dominio que estavam sem camada de aplicacao.
- Uso exclusivo de DTOs entre adapter de entrada e camada de aplicacao.
- Mapeamento centralizado no `Mapper`, reduzindo logica de transformacao no controller/service.
- Seguranca desacoplada em adapter de infraestrutura, preservando regras de dominio/aplicacao isoladas.

## Validacao

- Lint dos arquivos alterados: sem erros.
- Compilacao Maven nao pode ser concluida no ambiente atual por ausencia de JDK (apenas JRE disponivel).

## Proximos Passos Recomendados

1. Aplicar a mesma padronizacao de nome de metodo `delete...` nos demais modulos.
2. Adicionar tratamento de excecoes de dominio (ex.: recurso nao encontrado) com `@ControllerAdvice`.
3. Cobrir `AuthService`, `UsuarioService` e novos services com testes de caso de uso (mock das portas de saida).
4. Adicionar refresh token e revogacao para cenarios de logout.
