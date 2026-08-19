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

    @Mapping(target = "idEvento", ignore = true)
    @Mapping(target = "idCategoria", ignore = true)
    @Mapping(target = "idSecretaria", ignore = true)
    @Mapping(target = "dataEvento", source = "data_evento")
    @Mapping(target = "horaInicioEvento", source = "hora_inicio_evento")
    @Mapping(target = "horaFimEventos", source = "hora_fim_evento")
    Evento toModel(EventoRequestDTO dto);

    @Mapping(target = "id_evento", source = "idEvento")
    @Mapping(target = "id_categoria", source = "idCategoria.idCategoria")
    @Mapping(target = "id_secretaria", source = "idSecretaria.idSecretaria")
    @Mapping(target = "data_evento", source = "dataEvento")
    @Mapping(target = "hora_inicio_evento", source = "horaInicioEvento")
    @Mapping(target = "hora_fim_evento", source = "horaFimEventos")
    EventoResponseDTO toResponse(Evento evento);

    @Mapping(target = "idEvento", ignore = true)
    @Mapping(target = "idCategoria", ignore = true)
    @Mapping(target = "idSecretaria", ignore = true)
    @Mapping(target = "dataEvento", source = "data_evento")
    @Mapping(target = "horaInicioEvento", source = "hora_inicio_evento")
    @Mapping(target = "horaFimEventos", source = "hora_fim_evento")
    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    void updateFromDto(
            EventoRequestDTO dto,
            @MappingTarget Evento evento
    );
}