package chronos.tech.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "materia")
@Table(name = "materia")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_materia;
    private String nome;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_nivem_formacao")
    private NivelFormacao id_nivel;

}
