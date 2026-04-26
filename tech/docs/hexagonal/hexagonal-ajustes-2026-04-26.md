# Reavaliacao Estrutural - 2026-04-26

## Objetivo

Reorganizar a estrutura do projeto apos a adicao de novas classes de dominio, corrigir pontos de persistencia que estavam quebrando o acesso ao banco e alinhar autenticacao/autorizacao com Spring Security + JWT considerando o novo modelo de perfis.

## Ajustes de Estrutura e Persistencia

### Entidades novas e corrigidas

- `Aula`: ajuste de relacionamento com `TemaAula` (`ManyToOne`) e `StatusAula` como `@Enumerated(EnumType.STRING)`.
- `ChamadaAula`: inclusao de chave primaria (`id_chamada_aula`) para tornar a entidade persistivel.
- `UsuarioPerfil`: inclusao de chave primaria (`id_usuario_perfil`) para tornar a entidade persistivel.
- `ParticipacaoEvento`: mantido o novo nome de classe (substituindo `Participacao`).

### Classes auxiliares de banco

- Inclusao de `mysql-connector-j` no `pom.xml` para suportar a configuracao atual do `application.properties` (MySQL).
- Ajuste de query com parametros nomeados (`@Param`) em repositórios Spring Data para evitar inconsistencias de binding.

## Novos Modulos Hexagonais Criados

Para cada classe nova foram criadas camadas completas: DTO request/response, mapper, port in (use case), service, port out (repository), adapter out (Spring Data + adapter) e controller:

- `TipoVinculo`
- `EnderecoPessoa`
- `Aula`
- `ChamadaAula`
- `UsuarioPerfil`

## Atualizacao de DTOs e Mappers Existentes

### Pessoa

- `PessoaRequestDTO` e `PessoaResponseDTO` foram ampliados para refletir os novos campos de dominio (`email`, `telefone`, `cpf`, `bolsista`, `url_foto_perfil`, datas e `tipo_vinculo_id`).
- `PessoaMapper` atualizado para:
  - mapear `tipo_vinculo_id` para entidade `TipoVinculo`;
  - mapear/normalizar o campo `cpf` conforme o tipo atual definido na entidade.

### Usuario

- `UsuarioRequestDTO` e `UsuarioResponseDTO` atualizados para refletir a nova forma de modelagem sem `perfil` direto no `Usuario`.
- `UsuarioMapper` ajustado para remover dependencia antiga de `PerfilAcessoMapper`.

## Security + JWT com Novo Modelo de Perfis

### Mudanca principal

Como o perfil deixou de estar diretamente em `Usuario`, a autorizacao passou a usar a tabela de vinculacao `UsuarioPerfil`.

### Alteracoes aplicadas

- `AuthRegisterRequestDTO` alterado para aceitar lista de perfis (`perfis_id`).
- `AuthService` atualizado para:
  - criar `Usuario`;
  - criar os vinculos `UsuarioPerfil` correspondentes aos perfis informados;
  - manter emissao de JWT no cadastro e login.
- `CustomUserDetailsService` atualizado para carregar perfis do usuario via `UsuarioPerfilRepository`.
- `CustomUserPrincipal` atualizado para gerar `GrantedAuthority` a partir dos perfis vinculados.

## Impacto Arquitetural

- Cobertura hexagonal expandida para novas entidades de negocio adicionadas recentemente.
- Camada de seguranca desacoplada da modelagem antiga de perfil direto no usuario.
- Estrutura preparada para evoluir autorizacao por papeis sem acoplamento ao contrato de autenticação.

## Observacoes de Validacao

- Nao foi possivel executar compilacao final no ambiente atual por ausencia de JDK (apenas JRE).
- Validacao por linter dos arquivos alterados nao apontou erros.
