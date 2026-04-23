package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.GestaoSecretariaRequestDTO;
import chronos.tech.application.dto.response.GestaoSecretariaResponseDTO;
import chronos.tech.domain.model.classes.GestaoSecretaria;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.Secretaria;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GestaoSecretariaMapper {
    @Mapping(target = "id_gestao_secretaria", ignore = true)
    @Mapping(target = "id_pessoa", source = "id_pessoa")
    @Mapping(target = "id_secretaria", source = "id_secretaria")
    GestaoSecretaria toModel(GestaoSecretariaRequestDTO dto);

    @Mapping(target = "id_pessoa", source = "id_pessoa.id_pessoa")
    @Mapping(target = "id_secretaria", source = "id_secretaria.id_secretaria")
    GestaoSecretariaResponseDTO toResponse(GestaoSecretaria gestaoSecretaria);

    @Mapping(target = "id_gestao_secretaria", ignore = true)
    @Mapping(target = "id_pessoa", source = "id_pessoa")
    @Mapping(target = "id_secretaria", source = "id_secretaria")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(GestaoSecretariaRequestDTO dto, @MappingTarget GestaoSecretaria gestaoSecretaria);

    default Pessoa mapPessoa(Long id_pessoa) {
        if (id_pessoa == null) return null;
        Pessoa pessoa = new Pessoa();
        pessoa.setId_pessoa(id_pessoa);
        return pessoa;
    }

    default Secretaria mapSecretaria(Long id_secretaria) {
        if (id_secretaria == null) return null;
        Secretaria secretaria = new Secretaria();
        secretaria.setId_secretaria(id_secretaria);
        return secretaria;
    }
}
