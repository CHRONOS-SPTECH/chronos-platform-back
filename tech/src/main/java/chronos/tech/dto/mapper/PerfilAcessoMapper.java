package chronos.tech.dto.mapper;

import chronos.tech.dto.CreatePerfilAcessoRequestDTO;
import chronos.tech.dto.PerfilAcessoResponseDTO;
import chronos.tech.model.classes.PerfilAcesso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PerfilAcessoMapper {

    PerfilAcesso toModel(CreatePerfilAcessoRequestDTO dto);

    PerfilAcessoResponseDTO toResponse(PerfilAcesso perfilAcesso);

}
