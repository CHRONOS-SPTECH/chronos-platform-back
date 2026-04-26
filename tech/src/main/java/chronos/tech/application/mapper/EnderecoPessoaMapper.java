package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.EnderecoPessoaRequestDTO;
import chronos.tech.application.dto.response.EnderecoPessoaResponseDTO;
import chronos.tech.domain.model.classes.EnderecoPessoa;
import chronos.tech.domain.model.classes.Pessoa;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EnderecoPessoaMapper {
    @Mapping(target = "idEndereco", ignore = true)
    @Mapping(target = "pessoa", source = "id_pessoa")
    @Mapping(target = "uf", source = "uf")
    EnderecoPessoa toModel(EnderecoPessoaRequestDTO dto);

    @Mapping(target = "id_endereco", source = "idEndereco")
    @Mapping(target = "id_pessoa", source = "pessoa.idPessoa")
    @Mapping(target = "uf", source = "uf")
    EnderecoPessoaResponseDTO toResponse(EnderecoPessoa enderecoPessoa);

    @Mapping(target = "idEndereco", ignore = true)
    @Mapping(target = "pessoa", source = "id_pessoa")
    @Mapping(target = "uf", source = "uf")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(EnderecoPessoaRequestDTO dto, @MappingTarget EnderecoPessoa enderecoPessoa);

    default Pessoa map(Long id_pessoa) {
        if (id_pessoa == null) return null;
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(Math.toIntExact(id_pessoa));
        return pessoa;
    }
}
