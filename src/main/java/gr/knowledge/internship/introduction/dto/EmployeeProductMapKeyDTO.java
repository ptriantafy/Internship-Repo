package gr.knowledge.internship.introduction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeProductMapKeyDTO {
	private String fullName;
	private Long id;

	public void fromEmployee(EmployeeDTO employee) {
		this.setId(employee.getId());
		this.setFullName(employee.getName().concat(employee.getSurname()));
	}

	@Override
	public String toString() {
		return this.getFullName();
	}
}
