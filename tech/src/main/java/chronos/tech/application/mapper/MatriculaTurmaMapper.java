package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.MatriculaTurmaRequestDTO;
import chronos.tech.application.dto.response.MatriculaTurmaResponseDTO;
import chronos.tech.domain.model.classes.MatriculaTurma;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.Turma;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MatriculaTurmaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turma", source = "id_turma")
    @Mapping(target = "pessoa", source = "id_pessoa")
    @Mapping(target = "dataMatricula", source = "data_matricula")
    MatriculaTurma toModel(MatriculaTurmaRequestDTO dto);

    @Mapping(target = "id_turma", source = "turma.idTurma")
    @Mapping(target = "id_pessoa", source = "pessoa.idPessoa")
    @Mapping(target = "data_matricula", source = "dataMatricula")
    MatriculaTurmaResponseDTO toResponse(MatriculaTurma matriculaTurma);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turma", source = "id_turma")
    @Mapping(target = "pessoa", source = "id_pessoa")
    @Mapping(target = "dataMatricula", source = "data_matricula")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(MatriculaTurmaRequestDTO dto, @MappingTarget MatriculaTurma matriculaTurma);

    default Turma mapTurma(Long id_turma) {
        if (id_turma == null) return null;
        Turma turma = new Turma();
        turma.setIdTurma(Math.toIntExact(id_turma));
        return turma;
    }

    default Pessoa mapPessoa(Long id_pessoa) {
        if (id_pessoa == null) return null;
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(Math.toIntExact(id_pessoa));
        return pessoa;
    }
}
