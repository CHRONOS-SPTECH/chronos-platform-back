package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.ParticipacaoRequestDTO;
import chronos.tech.application.dto.response.ParticipacaoResponseDTO;
import chronos.tech.domain.model.classes.Evento;
import chronos.tech.domain.model.classes.Participacao;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.TipoParticipacao;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ParticipacaoMapper {
    @Mapping(target = "id_participacao", ignore = true)
    @Mapping(target = "id_evento", source = "id_evento")
    @Mapping(target = "id_pessoa", source = "id_pessoa")
    @Mapping(target = "id_tipo_participacao", source = "id_tipo_participacao")
    Participacao toModel(ParticipacaoRequestDTO dto);

    @Mapping(target = "id_evento", source = "id_evento.id_evento")
    @Mapping(target = "id_pessoa", source = "id_pessoa.id_pessoa")
    @Mapping(target = "id_tipo_participacao", source = "id_tipo_participacao.id_tipo_participacao")
    ParticipacaoResponseDTO toResponse(Participacao participacao);

    @Mapping(target = "id_participacao", ignore = true)
    @Mapping(target = "id_evento", source = "id_evento")
    @Mapping(target = "id_pessoa", source = "id_pessoa")
    @Mapping(target = "id_tipo_participacao", source = "id_tipo_participacao")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ParticipacaoRequestDTO dto, @MappingTarget Participacao participacao);

    default Evento mapEvento(Long id_evento) {
        if (id_evento == null) return null;
        Evento evento = new Evento();
        evento.setId_evento(id_evento);
        return evento;
    }

    default Pessoa mapPessoa(Long id_pessoa) {
        if (id_pessoa == null) return null;
        Pessoa pessoa = new Pessoa();
        pessoa.setId_pessoa(id_pessoa);
        return pessoa;
    }

    default TipoParticipacao mapTipoParticipacao(Long id_tipo_participacao) {
        if (id_tipo_participacao == null) return null;
        TipoParticipacao tipoParticipacao = new TipoParticipacao();
        tipoParticipacao.setId_tipo_participacao(id_tipo_participacao);
        return tipoParticipacao;
    }
}
