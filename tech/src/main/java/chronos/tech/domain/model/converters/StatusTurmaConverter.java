package chronos.tech.domain.model.converters;

import chronos.tech.domain.model.enums.StatusTurma;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusTurmaConverter implements AttributeConverter<StatusTurma, String> {

    @Override
    public String convertToDatabaseColumn(StatusTurma attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public StatusTurma convertToEntityAttribute(String dbData) {
        return StatusTurma.fromString(dbData);
    }
}
