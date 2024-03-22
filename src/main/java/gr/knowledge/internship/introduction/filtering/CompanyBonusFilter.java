package gr.knowledge.internship.introduction.filtering;

import org.springdoc.core.annotations.ParameterObject;

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
}
