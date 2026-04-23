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
    @Mapping(target = "id_matricula_turma", ignore = true)
    @Mapping(target = "id_turma", source = "id_turma")
    @Mapping(target = "id_pessoa", source = "id_pessoa")
    MatriculaTurma toModel(MatriculaTurmaRequestDTO dto);

    @Mapping(target = "id_turma", source = "id_turma.id_turma")
    @Mapping(target = "id_pessoa", source = "id_pessoa.id_pessoa")
    MatriculaTurmaResponseDTO toResponse(MatriculaTurma matriculaTurma);

    @Mapping(target = "id_matricula_turma", ignore = true)
    @Mapping(target = "id_turma", source = "id_turma")
    @Mapping(target = "id_pessoa", source = "id_pessoa")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(MatriculaTurmaRequestDTO dto, @MappingTarget MatriculaTurma matriculaTurma);

    default Turma mapTurma(Long id_turma) {
        if (id_turma == null) return null;
        Turma turma = new Turma();
        turma.setId_turma(id_turma);
        return turma;
    }

    default Pessoa mapPessoa(Long id_pessoa) {
        if (id_pessoa == null) return null;
        Pessoa pessoa = new Pessoa();
        pessoa.setId_pessoa(id_pessoa);
        return pessoa;
    }
}
