package chronos.tech.domain.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

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
        private String titulo;
        @Column(name = "descricao", columnDefinition = "TEXT")
        private String descricao;
        @Column(name = "local_evento")
        private String localEvento;
        @Column(name = "incluir_publico_externo", nullable = false)
        private Boolean incluirPublicoExterno = false;
        @Column(name = "data_evento")
        private LocalDate dataEvento;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "id_categoria", nullable = false)
        private CategoriaAtividade idCategoria;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "id_secretaria", nullable = false)
        private Secretaria idSecretaria;
        
        @Column(name = "hora_inicio_evento")
        private LocalTime horaInicioEvento;
        @Column(name = "hora_fim_evento")
        private LocalTime horaFimEventos;

        @OneToMany(mappedBy = "evento")
        private List<ParticipacaoEvento> participacaoEventoLong;

        @ManyToMany
        @JoinTable(
                name = "evento_turma",
                joinColumns = @JoinColumn(name = "id_evento"),
                inverseJoinColumns = @JoinColumn(name = "id_turma")
        )
        private Set<Turma> turmas = new HashSet<>();

}
