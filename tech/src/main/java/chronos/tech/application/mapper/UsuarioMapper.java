package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.UsuarioRequestDTO;
import chronos.tech.application.dto.response.UsuarioResponseDTO;
import chronos.tech.domain.model.classes.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {PessoaMapper.class})
public interface UsuarioMapper {

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "statusAtivo", expression = "java(Boolean.TRUE)")
    @Mapping(target = "emailLogin", source = "email_login")
    @Mapping(target = "senhaHash", source = "senha_hash")
    Usuario toModel(UsuarioRequestDTO dto);

    @Mapping(target = "id_usuario", source = "idUsuario")
    @Mapping(target = "email_login", source = "emailLogin")
    @Mapping(target = "status_ativo", source = "statusAtivo")
    @Mapping(target = "data_criacao", source = "dataCriacao")
    UsuarioResponseDTO toResponse(Usuario usuario);

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "statusAtivo", ignore = true)
    @Mapping(target = "emailLogin", source = "email_login")
    @Mapping(target = "senhaHash", source = "senha_hash")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UsuarioRequestDTO dto, @MappingTarget Usuario usuario);
}
