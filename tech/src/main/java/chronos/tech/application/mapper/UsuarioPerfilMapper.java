package chronos.tech.application.mapper;

import chronos.tech.application.dto.request.UsuarioPerfilRequestDTO;
import chronos.tech.application.dto.response.UsuarioPerfilResponseDTO;
import chronos.tech.domain.model.classes.PerfilAcesso;
import chronos.tech.domain.model.classes.Usuario;
import chronos.tech.domain.model.classes.UsuarioPerfil;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UsuarioPerfilMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", source = "id_usuario")
    @Mapping(target = "perfil", source = "id_perfil")
    UsuarioPerfil toModel(UsuarioPerfilRequestDTO dto);

    @Mapping(target = "id_usuario_perfil", ignore = true)
    @Mapping(target = "id_usuario", source = "usuario.idUsuario")
    @Mapping(target = "id_perfil", source = "perfil.idPerfil")
    UsuarioPerfilResponseDTO toResponse(UsuarioPerfil usuarioPerfil);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", source = "id_usuario")
    @Mapping(target = "perfil", source = "id_perfil")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UsuarioPerfilRequestDTO dto, @MappingTarget UsuarioPerfil usuarioPerfil);

    default Usuario mapUsuario(Long id_usuario) {
        if (id_usuario == null) return null;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(Math.toIntExact(id_usuario));
        return usuario;
    }

    default PerfilAcesso mapPerfil(Long id_perfil) {
        if (id_perfil == null) return null;
        PerfilAcesso perfil = new PerfilAcesso();
        perfil.setIdPerfil(Math.toIntExact(id_perfil));
        return perfil;
    }
}
