    package chronos.tech.dto.mapper;

    import chronos.tech.dto.CreateUsuarioRequestDTO;
    import chronos.tech.dto.UsuarioResponseDTO;
    import chronos.tech.model.classes.Usuario;
    import org.mapstruct.Mapper;

    @Mapper(componentModel = "spring")
    public interface UsuarioMapper {

        Usuario toModel(CreateUsuarioRequestDTO dto);

        UsuarioResponseDTO toResponse(Usuario usuario);
    }
