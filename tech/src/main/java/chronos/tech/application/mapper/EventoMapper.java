package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.EventoRequestDTO;
import chronos.tech.application.dto.response.EventoResponseDTO;
import chronos.tech.domain.model.classes.Evento;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EventoMapper {

    @Mapping(target = "id_evento", ignore = true)
    Evento toModel(EventoRequestDTO dto);

    EventoResponseDTO toResponse(Evento evento);

    @Mapping(target = "id_evento", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(EventoRequestDTO dto, @MappingTarget Evento evento);
}
