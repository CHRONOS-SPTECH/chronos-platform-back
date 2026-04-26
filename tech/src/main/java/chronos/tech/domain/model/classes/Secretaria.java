package chronos.tech.domain.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "secretaria")
@Table(name = "secretaria")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Secretaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_secretaria")
    private Integer idSecretaria;
    @Column(name = "nome_secretaria")
    private String nomeSecretaria;
    @Column(name = "descricao_secretaria")
    private String descricaoSecretaria;

    @OneToMany(mappedBy = "secretaria")
    private List<GestaoSecretaria> gestaoSecretarias;

}
