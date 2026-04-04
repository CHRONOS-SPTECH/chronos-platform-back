package chronos.tech.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "perfil_acesso")
@Table(name = "perfil_acesso")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PerfilAcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_perfil;

    private String nome_perfil;
    private String descricao;

}
