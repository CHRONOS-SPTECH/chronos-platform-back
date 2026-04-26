package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.ParticipacaoRequestDTO;
import chronos.tech.application.dto.response.ParticipacaoResponseDTO;
import chronos.tech.domain.model.classes.Evento;
import chronos.tech.domain.model.classes.ParticipacaoEvento;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.TipoParticipacao;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ParticipacaoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "evento", source = "id_evento")
    @Mapping(target = "pessoa", source = "id_pessoa")
    @Mapping(target = "idTipoParticipacao", source = "id_tipo_participacao")
    ParticipacaoEvento toModel(ParticipacaoRequestDTO dto);

    @Mapping(target = "id_participacao", ignore = true)
    @Mapping(target = "id_evento", source = "evento.idEvento")
    @Mapping(target = "id_pessoa", source = "pessoa.idPessoa")
    @Mapping(target = "id_tipo_participacao", source = "idTipoParticipacao.idTipoParticipacao")
    ParticipacaoResponseDTO toResponse(ParticipacaoEvento participacaoEvento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "evento", source = "id_evento")
    @Mapping(target = "pessoa", source = "id_pessoa")
    @Mapping(target = "idTipoParticipacao", source = "id_tipo_participacao")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ParticipacaoRequestDTO dto, @MappingTarget ParticipacaoEvento participacaoEvento);

    default Evento mapEvento(Long id_evento) {
        if (id_evento == null) return null;
        Evento evento = new Evento();
        evento.setIdEvento(Math.toIntExact(id_evento));
        return evento;
    }

    default Pessoa mapPessoa(Long id_pessoa) {
        if (id_pessoa == null) return null;
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(Math.toIntExact(id_pessoa));
        return pessoa;
    }

    default TipoParticipacao mapTipoParticipacao(Long id_tipo_participacao) {
        if (id_tipo_participacao == null) return null;
        TipoParticipacao tipoParticipacao = new TipoParticipacao();
        tipoParticipacao.setIdTipoParticipacao(Math.toIntExact(id_tipo_participacao));
        return tipoParticipacao;
    }
}
