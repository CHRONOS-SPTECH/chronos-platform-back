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

    @Mapping(target = "idTipoParticipacao", ignore = true)
    @Mapping(target = "tipoHoraGerada", source = "tipo_hora_gerada")
    TipoParticipacao toModel(TipoParticipacaoRequestDTO dto);

    @Mapping(target = "tipo_hora_gerada", source = "tipoHoraGerada")
    TipoParticipacaoResponseDTO toResponse(TipoParticipacao tipoParticipacao);

    @Mapping(target = "idTipoParticipacao", ignore = true)
    @Mapping(target = "tipoHoraGerada", source = "tipo_hora_gerada")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(TipoParticipacaoRequestDTO dto, @MappingTarget TipoParticipacao tipoParticipacao);
}
