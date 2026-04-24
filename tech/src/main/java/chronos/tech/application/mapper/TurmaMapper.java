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

    @Mapping(target = "id_turma", ignore = true)
    Turma toModel(TurmaRequestDTO dto);

    TurmaResponseDTO toResponse(Turma turma);

    @Mapping(target = "id_turma", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(TurmaRequestDTO dto, @MappingTarget Turma turma);
}
