package chronos.tech.domain.model.converters;

import chronos.tech.domain.model.enums.StatusTurma;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusTurmaConverter implements AttributeConverter<StatusTurma, String> {

    @Override
    public String convertToDatabaseColumn(StatusTurma attribute) {
        if (attribute == null) return null;
        return switch (attribute) {
            case NAO_INICIADA -> "Nao Iniciada";
            case EM_ANDAMENTO -> "Em Andamento";
            case CONCLUIDA -> "Concluida";
            case INATIVA -> "Inativa";
        };
    }

    @Override
    public StatusTurma convertToEntityAttribute(String dbData) {
        return StatusTurma.fromString(dbData);
    }
}
