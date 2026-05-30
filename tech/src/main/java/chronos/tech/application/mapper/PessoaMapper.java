package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.PessoaRequestDTO;
import chronos.tech.application.dto.response.PessoaResponseDTO;
import chronos.tech.application.dto.response.PessoaResumidoResponseDTO;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.TipoVinculo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    @Mapping(target = "idPessoa", ignore = true)
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "tipoVinculo", source = "tipo_vinculo_id")
    @Mapping(target = "urlFotoPerfil", source = "url_foto_perfil")
    @Mapping(target = "dataNascimento", source = "data_nascimento")
    @Mapping(target = "dataIngresso", source = "data_ingresso")
    @Mapping(target = "dataMembro", source = "data_membro")
    @Mapping(target = "dataSaida", source = "data_saida")
    Pessoa toModel(PessoaRequestDTO dto);

    @Mapping(target = "id_pessoa", source = "idPessoa")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "tipo_vinculo_id", source = "tipoVinculo.idTipoVinculo")
    @Mapping(target = "url_foto_perfil", source = "urlFotoPerfil")
    @Mapping(target = "data_nascimento", source = "dataNascimento")
    @Mapping(target = "data_ingresso", source = "dataIngresso")
    @Mapping(target = "data_membro", source = "dataMembro")
    @Mapping(target = "data_saida", source = "dataSaida")
    PessoaResponseDTO toResponse(Pessoa pessoa);

    @Mapping(target = "idPessoa", ignore = true)
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "tipoVinculo", source = "tipo_vinculo_id")
    @Mapping(target = "urlFotoPerfil", source = "url_foto_perfil")
    @Mapping(target = "dataNascimento", source = "data_nascimento")
    @Mapping(target = "dataIngresso", source = "data_ingresso")
    @Mapping(target = "dataMembro", source = "data_membro")
    @Mapping(target = "dataSaida", source = "data_saida")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PessoaRequestDTO dto, @MappingTarget Pessoa pessoa);

    @Mapping(target = "id_pessoa", source = "idPessoa")
    @Mapping(target = "url_foto_perfil", source = "urlFotoPerfil")
    PessoaResumidoResponseDTO toResumidoResponse(Pessoa pessoa);

    default TipoVinculo map(Integer tipo_vinculo_id) {
        if (tipo_vinculo_id == null) return null;
        TipoVinculo tipoVinculo = new TipoVinculo();
        tipoVinculo.setIdTipoVinculo(tipo_vinculo_id);
        return tipoVinculo;
    }
}
