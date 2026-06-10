package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.AulaRequestDTO;
import chronos.tech.application.dto.response.AulaResponseDTO;
import chronos.tech.domain.model.classes.Aula;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.TemaAula;
import chronos.tech.domain.model.classes.Turma;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AulaMapper {

    @Mapping(target = "idAula", ignore = true)
    @Mapping(target = "turma", source = "id_turma")
    @Mapping(target = "statusAula", ignore = true)
    @Mapping(target = "tema", source = "id_tema")
    @Mapping(target = "instrutor", source = "id_instrutor")
    @Mapping(target = "dataAula", source = "data_aula")
    @Mapping(target = "horaInicio", source = "hora_inicio")
    @Mapping(target = "horaFim", source = "hora_fim")
    @Mapping(target = "data_criacao_registro", source = "data_criacao_registro")
    @Mapping(target = "chamadaAulas", ignore = true)
    Aula toModel(AulaRequestDTO dto);

    @Mapping(target = "id_aula", source = "idAula")
    @Mapping(target = "id_turma", source = "turma.idTurma")
    @Mapping(target = "id_tema", source = "tema.idTema")
    @Mapping(target = "id_instrutor", source = "instrutor.idPessoa")
    @Mapping(target = "data_aula", source = "dataAula")
    @Mapping(target = "hora_inicio", source = "horaInicio")
    @Mapping(target = "hora_fim", source = "horaFim")
    @Mapping(target = "data_criacao_registro", source = "data_criacao_registro")
    AulaResponseDTO toResponse(Aula aula);

    @Mapping(target = "idAula", ignore = true)
    @Mapping(target = "turma", source = "id_turma")
    @Mapping(target = "tema", source = "id_tema")
    @Mapping(target = "instrutor", source = "id_instrutor")
    @Mapping(target = "dataAula", source = "data_aula")
    @Mapping(target = "horaInicio", source = "hora_inicio")
    @Mapping(target = "horaFim", source = "hora_fim")
    @Mapping(target = "data_criacao_registro", source = "data_criacao_registro")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AulaRequestDTO dto, @MappingTarget Aula aula);

    default Turma mapTurma(Long id_turma) {
        if (id_turma == null) return null;
        Turma turma = new Turma();
        turma.setIdTurma(Math.toIntExact(id_turma));
        return turma;
    }

    default TemaAula mapTema(Long id_tema) {
        if (id_tema == null) return null;
        TemaAula tema = new TemaAula();
        tema.setIdTema(Math.toIntExact(id_tema));
        return tema;
    }

    default Pessoa mapInstrutor(Long id_instrutor) {
        if (id_instrutor == null) return null;
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(Math.toIntExact(id_instrutor));
        return pessoa;
    }
}