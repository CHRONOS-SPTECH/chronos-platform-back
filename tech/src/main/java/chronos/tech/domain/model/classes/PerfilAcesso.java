package chronos.tech.domain.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "perfil_acesso")
@Table(name = "perfil_acesso")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PerfilAcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Integer idPerfil;
    @Column(name = "nome_perfil")
    private String nomePerfil;
    private String descricao;

    @OneToMany(mappedBy = "perfil")
    private List<UsuarioPerfil> usuarioPerfis;

}
