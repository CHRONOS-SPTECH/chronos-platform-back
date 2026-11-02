package chronos.tech.application.mapper;

import chronos.tech.application.dto.pessoa.PessoaRequestDTO;
import chronos.tech.application.dto.pessoa.PessoaRegistroRequestDTO;
import chronos.tech.application.dto.pessoa.PessoaResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaDetalhadaResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaResumidoResponseDTO;
import chronos.tech.application.dto.cadastro.TipoVinculoResponseDTO;
import chronos.tech.application.dto.pessoa.PessoaFichaResponseDTO;
import chronos.tech.application.dto.pessoa.EnderecoPessoaResponseDTO;
import chronos.tech.application.dto.pessoa.TurmaPessoaDetalheResponseDTO;
import chronos.tech.application.dto.pessoa.HistoricoAcademicoDetalheResponseDTO;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.model.classes.TipoVinculo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

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
    @Mapping(target = "biometriaFacial", ignore = true)
    Pessoa toModel(PessoaRequestDTO dto);

    @Mapping(target = "idPessoa", ignore = true)
    @Mapping(target = "tipoVinculo", source = "tipo_vinculo_id")
    @Mapping(target = "urlFotoPerfil", ignore = true)
    @Mapping(target = "dataNascimento", source = "data_nascimento")
    @Mapping(target = "dataIngresso", source = "data_ingresso")
    @Mapping(target = "dataMembro", source = "data_membro")
    @Mapping(target = "dataSaida", source = "data_saida")
    @Mapping(target = "biometriaFacial", ignore = true)
    @Mapping(target = "matriculaTurmas", ignore = true)
    @Mapping(target = "chamadaAulas", ignore = true)
    @Mapping(target = "gestaoSecretarias", ignore = true)
    @Mapping(target = "participacaoEventos", ignore = true)
    Pessoa toModel(PessoaRegistroRequestDTO dto);

    @Mapping(target = "id_pessoa", source = "idPessoa")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "tipo_vinculo_id", source = "tipoVinculo.idTipoVinculo")
    @Mapping(target = "url_foto_perfil", source = "urlFotoPerfil")
    @Mapping(target = "data_nascimento", source = "dataNascimento")
    @Mapping(target = "data_ingresso", source = "dataIngresso")
    @Mapping(target = "data_membro", source = "dataMembro")
    @Mapping(target = "data_saida", source = "dataSaida")
    PessoaResponseDTO toResponse(Pessoa pessoa);

    default PessoaDetalhadaResponseDTO toDetalhadaResponse(Pessoa pessoa) {
        TipoVinculoResponseDTO vinculo = pessoa.getTipoVinculo() == null ? null
                : new TipoVinculoResponseDTO(
                        pessoa.getTipoVinculo().getIdTipoVinculo(),
                        pessoa.getTipoVinculo().getNome_vinculo(),
                        pessoa.getTipoVinculo().getDescricao());
        return new PessoaDetalhadaResponseDTO(
                pessoa.getIdPessoa(), pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone(),
                pessoa.getGenero(), pessoa.getCpf(), pessoa.getBolsista(), pessoa.getUrlFotoPerfil(),
                pessoa.getDataNascimento(), pessoa.getDataIngresso(), pessoa.getDataMembro(),
                pessoa.getDataSaida(), vinculo);
    }

    default PessoaFichaResponseDTO toFichaResponse(
            Pessoa pessoa,
            EnderecoPessoaResponseDTO endereco,
            List<TurmaPessoaDetalheResponseDTO> turmas,
            List<HistoricoAcademicoDetalheResponseDTO> historicoAcademico) {
        PessoaDetalhadaResponseDTO dados = toDetalhadaResponse(pessoa);
        long totalChamadas = turmas.stream().mapToLong(TurmaPessoaDetalheResponseDTO::total_chamadas).sum();
        long presencas = turmas.stream().mapToLong(TurmaPessoaDetalheResponseDTO::presencas).sum();
        double percentualPresenca = totalChamadas == 0 ? 0.0 : presencas * 100.0 / totalChamadas;
        return new PessoaFichaResponseDTO(
                dados.id_pessoa(), dados.nome(), dados.email(), dados.telefone(), dados.genero(),
                dados.cpf(), dados.bolsista(), dados.url_foto_perfil(), dados.data_nascimento(),
                dados.data_ingresso(), dados.data_membro(), dados.data_saida(), dados.vinculo(),
                endereco, pessoa.getBiometriaFacial() != null && !pessoa.getBiometriaFacial().isBlank(),
                turmas, totalChamadas, presencas, percentualPresenca, historicoAcademico);
    }

    @Mapping(target = "idPessoa", ignore = true)
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "tipoVinculo", source = "tipo_vinculo_id")
    @Mapping(target = "urlFotoPerfil", source = "url_foto_perfil")
    @Mapping(target = "dataNascimento", source = "data_nascimento")
    @Mapping(target = "dataIngresso", source = "data_ingresso")
    @Mapping(target = "dataMembro", source = "data_membro")
    @Mapping(target = "dataSaida", source = "data_saida")
    @Mapping(target = "biometriaFacial", ignore = true)
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
