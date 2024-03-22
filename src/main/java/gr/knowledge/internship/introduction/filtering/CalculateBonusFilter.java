package gr.knowledge.internship.introduction.filtering;

import java.math.BigDecimal;

import org.springdoc.core.annotations.ParameterObject;

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
}
