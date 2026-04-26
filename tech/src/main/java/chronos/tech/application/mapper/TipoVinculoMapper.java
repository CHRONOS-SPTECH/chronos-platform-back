package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.TipoVinculoRequestDTO;
import chronos.tech.application.dto.response.TipoVinculoResponseDTO;
import chronos.tech.domain.model.classes.TipoVinculo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TipoVinculoMapper {
    @Mapping(target = "idTipoVinculo", ignore = true)
    @Mapping(target = "nome_vinculo", source = "nome_vinculo")
    TipoVinculo toModel(TipoVinculoRequestDTO dto);

    @Mapping(target = "id_tipo_vinculo", source = "idTipoVinculo")
    TipoVinculoResponseDTO toResponse(TipoVinculo tipoVinculo);

    @Mapping(target = "idTipoVinculo", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(TipoVinculoRequestDTO dto, @MappingTarget TipoVinculo tipoVinculo);
}
