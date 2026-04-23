package chronos.tech.domain.model.classes;

import chronos.tech.domain.model.enums.CargoAcesso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "gestao_secretaria")
@Table(name = "gestao_secretaria")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GestaoSecretaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_gestao_secretaria;
    @ManyToOne
    @JoinColumn(name = "fk_pessoa")
    private Pessoa id_pessoa;
    @ManyToOne
    @JoinColumn(name = "fk_secretaria")
    private Secretaria id_secretaria;
    @Enumerated(EnumType.STRING)
    private CargoAcesso cargo_acesso;

}
