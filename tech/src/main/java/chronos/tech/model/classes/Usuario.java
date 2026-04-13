package chronos.tech.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "usuarios")
@Entity(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_pessoa")
    private Pessoa pessoa;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_perfil")
    private PerfilAcesso perfil;
    private String email_login;
    private String senha_hash;
    private Boolean status_ativo;
    private LocalDateTime data_criacao;

}
