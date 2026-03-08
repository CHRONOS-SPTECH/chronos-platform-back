package chronos.tech.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "secretaria")
@Table(name = "secretaria")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Secretaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_secretaria;
    private String nome_secretaria;
    private String descricao_secretaria;

}
