package gr.knowledge.internship.introduction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
	private Long id;
	private String name;
	private String surname;
	private String email;
	private LocalDate startDate;
	private int vacationDays;
	private BigDecimal salary;
	private String employmentType;
	private CompanyDTO company;
}
