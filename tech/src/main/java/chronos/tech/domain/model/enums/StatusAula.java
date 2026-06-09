package chronos.tech.domain.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusAula {
    AGENDADA("Agendada"),
    CANCELADA("Cancelada"),
    FERIADO("Feriado");

    private final String displayName;

    StatusAula(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    @JsonCreator
    public static StatusAula fromString(String value) {
        if (value == null || value.trim().isEmpty()) return null;

        String valorLimpo = value.trim();

        for (StatusAula status : StatusAula.values()) {
            if (status.name().equalsIgnoreCase(valorLimpo) || status.getDisplayName().equalsIgnoreCase(valorLimpo)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Status desconhecido: " + value);
    }
}