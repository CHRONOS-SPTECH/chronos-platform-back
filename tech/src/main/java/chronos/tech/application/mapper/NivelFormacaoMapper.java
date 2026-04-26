package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.NivelFormacaoRequestDTO;
import chronos.tech.application.dto.response.NivelFormacaoResponseDTO;
import chronos.tech.domain.model.classes.NivelFormacao;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface NivelFormacaoMapper {

    @Mapping(target = "idNivel", ignore = true)
    @Mapping(target = "nomeNivel", source = "nome_nivel")
    @Mapping(target = "ordemHierarquia", source = "ordem_hierarquia")
    NivelFormacao toModel(NivelFormacaoRequestDTO dto);

    @Mapping(target = "id_nivel", source = "idNivel")
    @Mapping(target = "nome_nivel", source = "nomeNivel")
    @Mapping(target = "ordem_hierarquia", source = "ordemHierarquia")
    NivelFormacaoResponseDTO toResponse(NivelFormacao nivelFormacao);

    @Mapping(target = "idNivel", ignore = true)
    @Mapping(target = "nomeNivel", source = "nome_nivel")
    @Mapping(target = "ordemHierarquia", source = "ordem_hierarquia")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(NivelFormacaoRequestDTO dto, @MappingTarget NivelFormacao nivelFormacao);
}
