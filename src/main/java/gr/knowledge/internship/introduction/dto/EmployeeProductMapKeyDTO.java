package gr.knowledge.internship.introduction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long id;

	@Override
	public boolean equals(Object o) {
		if ((o == null) || !(o instanceof EmployeeProductMapKeyDTO)) {
			return false;
		}

		final EmployeeProductMapKeyDTO obj = (EmployeeProductMapKeyDTO) o;
		return obj.getId() == this.id;
	}

	public void fromEmployee(EmployeeDTO employee) {
		this.setId(employee.getId());
		this.setFullName(employee.getName().concat(employee.getSurname()));
	}

	@Override
	public String toString() {
		return this.getFullName();
	}
}
