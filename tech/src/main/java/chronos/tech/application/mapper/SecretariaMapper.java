package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.SecretariaRequestDTO;
import chronos.tech.application.dto.response.SecretariaResponseDTO;
import chronos.tech.domain.model.classes.Secretaria;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SecretariaMapper {

    @Mapping(target = "idSecretaria", ignore = true)
    @Mapping(target = "nomeSecretaria", source = "nome_secretaria")
    @Mapping(target = "descricaoSecretaria", source = "descricao_secretaria")
    Secretaria toModel(SecretariaRequestDTO dto);

    @Mapping(target = "id_secretaria", source = "idSecretaria")
    @Mapping(target = "nome_secretaria", source = "nomeSecretaria")
    @Mapping(target = "descricao_secretaria", source = "descricaoSecretaria")
    SecretariaResponseDTO toResponse(Secretaria secretaria);

    @Mapping(target = "idSecretaria", ignore = true)
    @Mapping(target = "nomeSecretaria", source = "nome_secretaria")
    @Mapping(target = "descricaoSecretaria", source = "descricao_secretaria")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(SecretariaRequestDTO dto, @MappingTarget Secretaria secretaria);
}
