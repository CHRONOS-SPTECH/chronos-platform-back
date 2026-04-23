package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.PessoaRequestDTO;
import chronos.tech.application.dto.response.PessoaResponseDTO;
import chronos.tech.domain.model.classes.Pessoa;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    @Mapping(target = "id_pessoa", ignore = true)
    @Mapping(target = "tipoVinculo", ignore = true)
    @Mapping(target = "data_ingresso", ignore = true)
    @Mapping(target = "data_membro", ignore = true)
    @Mapping(target = "data_saida", ignore = true)
    Pessoa toModel(PessoaRequestDTO dto);

    @Mapping(target = "dataNascimento", source = "data_nascimento")
    PessoaResponseDTO toResponse(Pessoa pessoa);

    @Mapping(target = "id_pessoa", ignore = true)
    @Mapping(target = "tipoVinculo", ignore = true)
    @Mapping(target = "data_ingresso", ignore = true)
    @Mapping(target = "data_membro", ignore = true)
    @Mapping(target = "data_saida", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PessoaRequestDTO dto, @MappingTarget Pessoa pessoa);
}
