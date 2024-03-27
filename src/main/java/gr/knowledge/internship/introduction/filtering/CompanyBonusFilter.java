package gr.knowledge.internship.introduction.filtering;

import org.springdoc.core.annotations.ParameterObject;

import gr.knowledge.internship.introduction.enums.BonusBySeason;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ParameterObject
public class CompanyBonusFilter {
	@Parameter(name="companyId")
	private int companyId;
	@Parameter(name="season")
	private String season;

	public void validateInput() {
		try {
			if (this.getCompanyId()< 0) {
				throw new IllegalArgumentException("Company ID cannot be negative");
			}
		} catch (NullPointerException npe) {
			throw new IllegalArgumentException("Company ID cannot be null");
		}
		BonusBySeason.resolveOfEnum(this.season);
	}
}
