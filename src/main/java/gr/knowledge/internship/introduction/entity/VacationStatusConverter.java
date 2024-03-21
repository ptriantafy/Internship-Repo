package gr.knowledge.internship.introduction.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class VacationStatusConverter implements AttributeConverter<VacationStatus, String> {
	@Override
	public String convertToDatabaseColumn(VacationStatus status) {
		return status.toDbValue();
	}
	@Override
	public VacationStatus convertToEntityAttribute(String status) {
		return VacationStatus.from(status);
	}
}