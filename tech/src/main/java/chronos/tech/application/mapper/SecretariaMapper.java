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

    @Mapping(target = "id_secretaria", ignore = true)
    Secretaria toModel(SecretariaRequestDTO dto);

    SecretariaResponseDTO toResponse(Secretaria secretaria);

    @Mapping(target = "id_secretaria", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(SecretariaRequestDTO dto, @MappingTarget Secretaria secretaria);
}
