package chronos.tech.domain.model.enums;

public enum StatusTurma {
    NAO_INICIADA("Não Iniciada"),
    EM_ANDAMENTO("Em Andamento"),
    CONCLUIDA("Concluída"),
    INATIVA("Inativa");
    
    private final String displayName;
    
    StatusTurma(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
    
    // Método para converter strings do banco para o enum
    public static StatusTurma fromString(String value) {
        if (value == null) return null;
        
        switch (value) {
            case "Não Iniciada":
                return NAO_INICIADA;
            case "Inativa":
                return INATIVA;
            case "Em Andamento":
                return EM_ANDAMENTO;
            case "Concluída":
                return CONCLUIDA;
            default:
                // Tenta converter pelo nome do enum como fallback
                try {
                    return StatusTurma.valueOf(value);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Unknown status: " + value);
                }
        }
    }
}
