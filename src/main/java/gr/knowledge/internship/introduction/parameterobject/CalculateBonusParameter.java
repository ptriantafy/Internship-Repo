package gr.knowledge.internship.introduction.parameterobject;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculateBonusParameter {
	private BigDecimal salary;
	private String season;
}
