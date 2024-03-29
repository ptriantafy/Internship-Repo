package gr.knowledge.internship.introduction.filtering;

import java.math.BigDecimal;

import org.springdoc.core.annotations.ParameterObject;

import gr.knowledge.internship.introduction.enums.BonusBySeason;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ParameterObject
public class CalculateBonusFilter {
	@Parameter(name = "salary")
	private BigDecimal salary;
	@Parameter(name = "season")
	private String season;

	@Override
	public String toString() {
		return "Season: " + this.getSeason() + " Salary: " + this.getSalary();
	}

	public void validateInput() {
		try {
			if (this.getSalary().compareTo(BigDecimal.ZERO) < 0) {
				throw new IllegalArgumentException("Salary cannot be negative: " + salary);
			}
		} catch (NullPointerException npe) {
			throw new IllegalArgumentException("Salary cannot be null");
		}
		BonusBySeason.resolveOfEnum(this.season);
	}

}
