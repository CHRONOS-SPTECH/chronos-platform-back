package chronos.tech.domain.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity(name = "evento")
@Table(name = "evento")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Evento {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_evento")
        private Integer idEvento;
        @ManyToOne(cascade = CascadeType.PERSIST)
        @JoinColumn(name = "id_categoria")
        private CategoriaAtividade idCategoria;
        @ManyToOne(cascade = CascadeType.PERSIST)
        @JoinColumn(name = "id_secretaria")
        private Secretaria idSecretaria;
        private String titulo;
        @Column(name = "data_evento")
        private LocalDate dataEvento;
        @Column(name = "hora_inicio_evento")
        private LocalTime horaInicioEvento;
        @Column(name = "hora_fim_evento")
        private LocalTime horaFimEventos;

        @OneToMany(mappedBy = "evento")
        private List<ParticipacaoEvento> participacaoEventoLong;

}
