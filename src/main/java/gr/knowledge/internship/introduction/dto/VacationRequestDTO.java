package gr.knowledge.internship.introduction.dto;

import java.time.LocalDate;

import gr.knowledge.internship.introduction.enums.VacationStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VacationRequestDTO {
	private Long id;
	private EmployeeDTO employee;
	private LocalDate startDate;
	private LocalDate endDate;
	@Setter(value = AccessLevel.NONE)
	private VacationStatus status;
	private int days;

	public void setStatus(VacationStatus status) {
		this.status = status;
	}
	
	public void setStatus(String status) {
		this.status = VacationStatus.valueOf(status.toUpperCase());
	}
}
