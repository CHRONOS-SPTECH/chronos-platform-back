package chronos.tech.domain.model.classes;

import chronos.tech.domain.model.classes.compostas.GestaoSecretariaId;
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

    @EmbeddedId
    private GestaoSecretariaId id;

    @ManyToOne
    @MapsId("idPessoa")
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;
    @ManyToOne
    @MapsId("idSecretaria")
    @JoinColumn(name = "id_secretaria")
    private Secretaria secretaria;
    @Enumerated(EnumType.STRING)
    @Column(name = "cargo_acesso")
    private CargoAcesso cargoAcesso;

}
