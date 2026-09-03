package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.PessoaRegistroRequestDTO;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.TipoVinculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface PessoaRegistroMapper {

    @Mapping(target = "idPessoa", ignore = true)
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(target = "genero", source = "genero")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "bolsista", source = "bolsista")
    @Mapping(target = "tipoVinculo", source = "tipo_vinculo_id")
    @Mapping(target = "dataNascimento", source = "data_nascimento", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "dataIngresso", source = "data_ingresso", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "dataMembro", source = "data_membro", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "dataSaida", source = "data_saida", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "urlFotoPerfil", ignore = true)
    @Mapping(target = "biometriaFacial", ignore = true)
    @Mapping(target = "matriculaTurmas", ignore = true)
    @Mapping(target = "chamadaAulas", ignore = true)
    @Mapping(target = "gestaoSecretarias", ignore = true)
    @Mapping(target = "participacaoEventos", ignore = true)
    Pessoa toModel(PessoaRegistroRequestDTO dto);

    default TipoVinculo mapTipoVinculo(Integer tipo_vinculo_id) {
        if (tipo_vinculo_id == null) return null;
        TipoVinculo tipoVinculo = new TipoVinculo();
        tipoVinculo.setIdTipoVinculo(tipo_vinculo_id);
        return tipoVinculo;
    }

    default LocalDate stringToLocalDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        return LocalDate.parse(date);
    }
}
