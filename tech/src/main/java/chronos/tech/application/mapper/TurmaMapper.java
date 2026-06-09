package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.TurmaRequestDTO;
import chronos.tech.application.dto.response.TurmaResponseDTO;
import chronos.tech.domain.model.classes.Turma;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TurmaMapper {

    @Mapping(target = "idTurma", ignore = true)
    @Mapping(target = "nomeTurma", source = "nome_turma")
    @Mapping(target = "dataInicio", source = "data_inicio")
    @Mapping(target = "dataEncerramento", source = "data_encerramento")
    @Mapping(target = "statusTurma", source = "status_turma")
    Turma toModel(TurmaRequestDTO dto);

    @Mapping(target = "id_turma", source = "idTurma")
    @Mapping(target = "nome_turma", source = "nomeTurma")
    @Mapping(target = "data_inicio", source = "dataInicio")
    @Mapping(target = "data_encerramento", source = "dataEncerramento")
    @Mapping(target = "status_turma", source = "statusTurma")
    @Mapping(
            target = "percentual_conclusao",
            expression = "java(turma.calcularPercentualConclusao())"
    )
    TurmaResponseDTO toResponse(Turma turma);

    @Mapping(target = "idTurma", ignore = true)
    @Mapping(target = "nomeTurma", source = "nome_turma")
    @Mapping(target = "dataInicio", source = "data_inicio")
    @Mapping(target = "dataEncerramento", source = "data_encerramento")
    @Mapping(target = "statusTurma", source = "status_turma")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(TurmaRequestDTO dto, @MappingTarget Turma turma);
}
