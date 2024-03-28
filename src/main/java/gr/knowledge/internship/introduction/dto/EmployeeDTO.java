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
	private CompanyDTO company;
	private String email;
	private String employmentType;
	private Long id;
	private String name;
	private BigDecimal salary;
	private LocalDate startDate;
	private String surname;
	private int vacationDays;
}
