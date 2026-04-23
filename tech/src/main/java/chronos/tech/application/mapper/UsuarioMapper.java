package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.UsuarioRequestDTO;
import chronos.tech.application.dto.response.UsuarioResponseDTO;
import chronos.tech.domain.model.classes.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {PessoaMapper.class, PerfilAcessoMapper.class})
public interface UsuarioMapper {

    @Mapping(target = "id_usuario", ignore = true)
    @Mapping(target = "perfil", source = "perfilAcesso")
    @Mapping(target = "data_criacao", ignore = true)
    @Mapping(target = "status_ativo", expression = "java(Boolean.TRUE)")
    Usuario toModel(UsuarioRequestDTO dto);

    @Mapping(target = "perfilAcesso", source = "perfil")
    UsuarioResponseDTO toResponse(Usuario usuario);

    @Mapping(target = "id_usuario", ignore = true)
    @Mapping(target = "perfil", source = "perfilAcesso")
    @Mapping(target = "data_criacao", ignore = true)
    @Mapping(target = "status_ativo", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UsuarioRequestDTO dto, @MappingTarget Usuario usuario);
}
