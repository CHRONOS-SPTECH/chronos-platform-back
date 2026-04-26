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
    @Mapping(target = "idTema", ignore = true)
    @Mapping(target = "idMateria", source = "id_materia")
    @Mapping(target = "tituloTema", source = "titulo_tema")
    @Mapping(target = "ordemPrevista", source = "ordem_prevista")
    TemaAula toModel(TemaAulaRequestDTO dto);

    @Mapping(target = "id_tema", source = "idTema")
    @Mapping(target = "id_materia", source = "idMateria.idMateria")
    @Mapping(target = "titulo_tema", source = "tituloTema")
    @Mapping(target = "ordem_prevista", source = "ordemPrevista")
    TemaAulaResponseDTO toResponse(TemaAula temaAula);

    @Mapping(target = "idTema", ignore = true)
    @Mapping(target = "idMateria", source = "id_materia")
    @Mapping(target = "tituloTema", source = "titulo_tema")
    @Mapping(target = "ordemPrevista", source = "ordem_prevista")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(TemaAulaRequestDTO dto, @MappingTarget TemaAula temaAula);

    default Materia map(Long id_materia) {
        if (id_materia == null) return null;
        Materia materia = new Materia();
        materia.setIdMateria(Math.toIntExact(id_materia));
        return materia;
    }
}
