package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.NivelFormacaoRequestDTO;
import chronos.tech.application.dto.response.NivelFormacaoResponseDTO;
import chronos.tech.domain.model.classes.NivelFormacao;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface NivelFormacaoMapper {

    @Mapping(target = "id_nivel", ignore = true)
    NivelFormacao toModel(NivelFormacaoRequestDTO dto);

    NivelFormacaoResponseDTO toResponse(NivelFormacao nivelFormacao);

    @Mapping(target = "id_nivel", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(NivelFormacaoRequestDTO dto, @MappingTarget NivelFormacao nivelFormacao);
}
