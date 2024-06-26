package gr.knowledge.internship.introduction.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BonusDTO {
	private BigDecimal amount;
	private CompanyDTO company;
	private EmployeeDTO employee;
	private Long id;
}
