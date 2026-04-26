package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.PerfilAcessoRequestDTO;
import chronos.tech.application.dto.response.PerfilAcessoResponseDTO;
import chronos.tech.domain.model.classes.PerfilAcesso;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PerfilAcessoMapper {

    @Mapping(target = "idPerfil", ignore = true)
    @Mapping(target = "nomePerfil", source = "nome_perfil")
    PerfilAcesso toModel(PerfilAcessoRequestDTO dto);

    @Mapping(target = "id_perfil", source = "idPerfil")
    @Mapping(target = "nome_perfil", source = "nomePerfil")
    PerfilAcessoResponseDTO toResponse(PerfilAcesso perfilAcesso);

    @Mapping(target = "idPerfil", ignore = true)
    @Mapping(target = "nomePerfil", source = "nome_perfil")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PerfilAcessoRequestDTO dto, @MappingTarget PerfilAcesso perfilAcesso);
}
