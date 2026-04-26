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
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pessoa", source = "id_pessoa")
    @Mapping(target = "secretaria", source = "id_secretaria")
    @Mapping(target = "cargoAcesso", source = "cargo_acesso")
    GestaoSecretaria toModel(GestaoSecretariaRequestDTO dto);

    @Mapping(target = "id_gestao_secretaria", ignore = true)
    @Mapping(target = "id_pessoa", source = "pessoa.idPessoa")
    @Mapping(target = "id_secretaria", source = "secretaria.idSecretaria")
    @Mapping(target = "cargo_acesso", source = "cargoAcesso")
    GestaoSecretariaResponseDTO toResponse(GestaoSecretaria gestaoSecretaria);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pessoa", source = "id_pessoa")
    @Mapping(target = "secretaria", source = "id_secretaria")
    @Mapping(target = "cargoAcesso", source = "cargo_acesso")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(GestaoSecretariaRequestDTO dto, @MappingTarget GestaoSecretaria gestaoSecretaria);

    default Pessoa mapPessoa(Long id_pessoa) {
        if (id_pessoa == null) return null;
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(Math.toIntExact(id_pessoa));
        return pessoa;
    }

    default Secretaria mapSecretaria(Long id_secretaria) {
        if (id_secretaria == null) return null;
        Secretaria secretaria = new Secretaria();
        secretaria.setIdSecretaria(Math.toIntExact(id_secretaria));
        return secretaria;
    }
}
