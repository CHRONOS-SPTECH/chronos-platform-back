package chronos.tech.domain.model.converters;

import chronos.tech.domain.model.enums.StatusAula;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusAulaConverter implements AttributeConverter<StatusAula, String> {

    @Override
    public String convertToDatabaseColumn(StatusAula attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public StatusAula convertToEntityAttribute(String dbData) {
        return StatusAula.fromString(dbData);
    }
}
