package chronos.tech.dto;


import chronos.tech.dto.request.CreatePessoaRequestDto;

public record CreateUsuarioRequestDTO(CreatePessoaRequestDto pessoa, CreatePerfilAcessoRequestDTO perfilAcesso, String email, String senha){
}