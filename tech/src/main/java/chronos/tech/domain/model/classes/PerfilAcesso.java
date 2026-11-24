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

    @Column(name = "status_ativo", columnDefinition = "TINYINT")
    private Boolean statusAtivo;

    @Column(name = "codigo_tela_inicial")
    private String codigoTelaInicial;

    @OneToMany(mappedBy = "perfil")
    private List<UsuarioPerfil> usuarioPerfis;

    @ManyToMany
    @JoinTable(
            name = "perfil_tela",
            joinColumns = @JoinColumn(name = "id_perfil"),
            inverseJoinColumns = @JoinColumn(name = "id_tela")
    )
    private List<Tela> telas;

}
