package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.EventoRequestDTO;
import chronos.tech.application.dto.response.EventoResponseDTO;
import chronos.tech.domain.model.classes.Evento;
import chronos.tech.domain.model.classes.Turma;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;
import chronos.tech.domain.model.classes.ParticipacaoEvento;

@Mapper(componentModel = "spring")
public interface EventoMapper {

    @Mapping(target = "idEvento", ignore = true)
    @Mapping(target = "idCategoria", ignore = true)
    @Mapping(target = "idSecretaria", ignore = true)
    @Mapping(target = "turmas", ignore = true)
    @Mapping(target = "dataEvento", source = "data_evento")
    @Mapping(target = "horaInicioEvento", source = "hora_inicio_evento")
    @Mapping(target = "horaFimEventos", source = "hora_fim_evento")
    @Mapping(target = "localEvento", source = "local_evento")
    Evento toModel(EventoRequestDTO dto);

    @Mapping(target = "id_evento", source = "idEvento")
    @Mapping(target = "id_categoria", source = "idCategoria.idCategoria")
    @Mapping(target = "id_secretaria", source = "idSecretaria.idSecretaria")
    @Mapping(target = "data_evento", source = "dataEvento")
    @Mapping(target = "hora_inicio_evento", source = "horaInicioEvento")
    @Mapping(target = "hora_fim_evento", source = "horaFimEventos")
    @Mapping(target = "local_evento", source = "localEvento")
    @Mapping(target = "id_turmas", source = "turmas")
    @Mapping(target = "incluir_publico_externo", source = "incluirPublicoExterno")
    @Mapping(
            target = "participantes",
            source = "participacaoEventoLong"
    )
    EventoResponseDTO toResponse(Evento evento);

    default List<Integer> mapTurmasToIds(Set<Turma> turmas) {
        if (turmas == null) return List.of();
        return turmas.stream().map(Turma::getIdTurma).sorted().toList();
    }

    @Mapping(target = "idEvento", ignore = true)
    @Mapping(target = "idCategoria", ignore = true)
    @Mapping(target = "idSecretaria", ignore = true)
    @Mapping(target = "turmas", ignore = true)
    @Mapping(target = "dataEvento", source = "data_evento")
    @Mapping(target = "horaInicioEvento", source = "hora_inicio_evento")
    @Mapping(target = "horaFimEventos", source = "hora_fim_evento")
    @Mapping(target = "localEvento", source = "local_evento")
    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    void updateFromDto(
            EventoRequestDTO dto,
            @MappingTarget Evento evento
    );

    default EventoResponseDTO.ParticipanteDTO toParticipante(
            ParticipacaoEvento participacao
    ) {
        if (participacao == null || participacao.getPessoa() == null) {
            return null;
        }

        return new EventoResponseDTO.ParticipanteDTO(
                participacao.getPessoa().getIdPessoa(),
                participacao.getPessoa().getNome(),
                participacao.getPessoa().getEmail(),
                participacao.getCompareceu()
        );
    }
}
