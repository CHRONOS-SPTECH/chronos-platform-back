package chronos.tech.domain.model.classes;

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
    @Column(name = "id_materia")
    private Integer idMateria;
    private String nome;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_nivel")
    private NivelFormacao idNivel;

}
