package chronos.tech.application.dto.response;

import lombok.Getter;

@Getter
public class AulaComTemaEMateriaResponseDTO {
    private AulaResponseDTO aula;
    private TemaAulaResponseDTO tema;
    private MateriaResponseDTO materia;
    private Boolean chamadaFeita;

    public AulaComTemaEMateriaResponseDTO(AulaResponseDTO aula, TemaAulaResponseDTO tema, MateriaResponseDTO materia, Boolean chamadaFeita) {
        this.aula = aula;
        this.tema = tema;
        this.materia = materia;
        this.chamadaFeita = chamadaFeita;
    }
}
