package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.MateriaRequestDTO;
import chronos.tech.application.dto.response.MateriaResponseDTO;
import chronos.tech.domain.model.classes.Materia;
import chronos.tech.domain.model.classes.NivelFormacao;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MateriaMapper {
    @Mapping(target = "idMateria", ignore = true)
    @Mapping(target = "idNivel", source = "id_nivel")
    Materia toModel(MateriaRequestDTO dto);

    @Mapping(target = "id_materia", source = "idMateria")
    @Mapping(target = "id_nivel", source = "idNivel.idNivel")
    MateriaResponseDTO toResponse(Materia materia);

    @Mapping(target = "idMateria", ignore = true)
    @Mapping(target = "idNivel", source = "id_nivel")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(MateriaRequestDTO dto, @MappingTarget Materia materia);

    default NivelFormacao map(Long id_nivel) {
        if (id_nivel == null) return null;
        NivelFormacao nivel = new NivelFormacao();
        nivel.setIdNivel(Math.toIntExact(id_nivel));
        return nivel;
    }
}
