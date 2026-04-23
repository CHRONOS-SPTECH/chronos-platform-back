package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.CategoriaAtividadeRequestDTO;
import chronos.tech.application.dto.response.CategoriaAtividadeResponseDTO;
import chronos.tech.domain.model.classes.CategoriaAtividade;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CategoriaAtividadeMapper {

    @Mapping(target = "id_categoria", ignore = true)
    CategoriaAtividade toModel(CategoriaAtividadeRequestDTO dto);

    CategoriaAtividadeResponseDTO toResponse(CategoriaAtividade categoriaAtividade);

    @Mapping(target = "id_categoria", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(CategoriaAtividadeRequestDTO dto, @MappingTarget CategoriaAtividade categoriaAtividade);
}
