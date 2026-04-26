package chronos.tech.domain.model.classes;

import chronos.tech.domain.model.classes.compostas.UsuarioPerfilId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "usuario_perfil")
@Table(name = "usuario_perfil")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioPerfil {

    @EmbeddedId
    private UsuarioPerfilId id;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne
    @MapsId("idPerfil")
    @JoinColumn(name = "id_perfil")
    private PerfilAcesso perfil;

}
