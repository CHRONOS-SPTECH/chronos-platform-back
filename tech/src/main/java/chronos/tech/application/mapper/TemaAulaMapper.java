package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.TemaAulaRequestDTO;
import chronos.tech.application.dto.response.TemaAulaResponseDTO;
import chronos.tech.domain.model.classes.Materia;
import chronos.tech.domain.model.classes.TemaAula;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TemaAulaMapper {
    @Mapping(target = "id_tema", ignore = true)
    @Mapping(target = "id_materia", source = "id_materia")
    TemaAula toModel(TemaAulaRequestDTO dto);

    @Mapping(target = "id_materia", source = "id_materia.id_materia")
    TemaAulaResponseDTO toResponse(TemaAula temaAula);

    @Mapping(target = "id_tema", ignore = true)
    @Mapping(target = "id_materia", source = "id_materia")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(TemaAulaRequestDTO dto, @MappingTarget TemaAula temaAula);

    default Materia map(Long id_materia) {
        if (id_materia == null) return null;
        Materia materia = new Materia();
        materia.setId_materia(id_materia);
        return materia;
    }
}
