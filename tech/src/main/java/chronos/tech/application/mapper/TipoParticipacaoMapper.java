package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.TipoParticipacaoRequestDTO;
import chronos.tech.application.dto.response.TipoParticipacaoResponseDTO;
import chronos.tech.domain.model.classes.TipoParticipacao;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TipoParticipacaoMapper {

    @Mapping(target = "id_tipo_participacao", ignore = true)
    TipoParticipacao toModel(TipoParticipacaoRequestDTO dto);

    TipoParticipacaoResponseDTO toResponse(TipoParticipacao tipoParticipacao);

    @Mapping(target = "id_tipo_participacao", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(TipoParticipacaoRequestDTO dto, @MappingTarget TipoParticipacao tipoParticipacao);
}
