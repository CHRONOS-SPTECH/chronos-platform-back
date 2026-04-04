package chronos.tech.model.classes;

import chronos.tech.model.enums.TipoVinculo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pessoa")
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pessoa;
    @NonNull
    private String nome;
    private String genero;
    private TipoVinculo tipoVinculo;
    private LocalDate data_nascimento;
    private LocalDate data_ingresso;
    private LocalDate data_membro;
    private LocalDate data_saida;
}


