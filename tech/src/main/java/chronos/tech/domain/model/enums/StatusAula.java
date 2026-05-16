package chronos.tech.domain.model.enums;

public enum StatusAula {
    AGENDADA("Agendada"),
    CANCELADA("Cancelada"),
    FERIADO("Feriado");
    
    private final String displayName;
    
    StatusAula(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
    
    public static StatusAula fromString(String value) {
        if (value == null) return null;
        
        switch (value) {
            case "Agendada":
                return AGENDADA;
            case "Cancelada":
                return CANCELADA;
            case "Feriado":
                return FERIADO;
            default:
                try {
                    return StatusAula.valueOf(value);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Unknown status: " + value);
                }
        }
    }
}
