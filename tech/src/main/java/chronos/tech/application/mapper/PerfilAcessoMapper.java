package chronos.tech.application.mapper;

import chronos.tech.application.dto.cadastro.PerfilAcessoRequestDTO;
import chronos.tech.application.dto.cadastro.PerfilAcessoResponseDTO;
import chronos.tech.application.dto.cadastro.TelaResponseDTO;
import chronos.tech.domain.model.classes.PerfilAcesso;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PerfilAcessoMapper {

    @Mapping(target = "idPerfil", ignore = true)
    @Mapping(target = "nomePerfil", source = "nome_perfil")
    @Mapping(target = "statusAtivo", source = "status_ativo")
    @Mapping(target = "codigoTelaInicial", source = "codigo_tela_inicial")
    @Mapping(target = "telas", ignore = true)
    PerfilAcesso toModel(PerfilAcessoRequestDTO dto);

    @Mapping(target = "id_perfil", source = "idPerfil")
    @Mapping(target = "nome_perfil", source = "nomePerfil")
    @Mapping(target = "status_ativo", source = "statusAtivo")
    @Mapping(target = "codigo_tela_inicial", source = "codigoTelaInicial")
    @Mapping(target = "telas", ignore = true)
    PerfilAcessoResponseDTO toResponse(PerfilAcesso perfilAcesso);

    default PerfilAcessoResponseDTO withTelas(PerfilAcesso perfilAcesso) {
        PerfilAcessoResponseDTO base = toResponse(perfilAcesso);
        java.util.List<TelaResponseDTO> telas = perfilAcesso.getTelas() == null
                ? java.util.List.of()
                : perfilAcesso.getTelas().stream()
                .map(tela -> new TelaResponseDTO(tela.getIdTela(), tela.getCodigoTela(), tela.getNomeTela(),
                        tela.getGrupo(), tela.getOrdem(), tela.getStatusAtivo()))
                .toList();
        return new PerfilAcessoResponseDTO(base.id_perfil(), base.nome_perfil(), base.descricao(),
                base.status_ativo(), base.codigo_tela_inicial(), telas);
    }

    @Mapping(target = "idPerfil", ignore = true)
    @Mapping(target = "nomePerfil", source = "nome_perfil")
    @Mapping(target = "statusAtivo", source = "status_ativo")
    @Mapping(target = "codigoTelaInicial", source = "codigo_tela_inicial")
    @Mapping(target = "telas", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PerfilAcessoRequestDTO dto, @MappingTarget PerfilAcesso perfilAcesso);
}
