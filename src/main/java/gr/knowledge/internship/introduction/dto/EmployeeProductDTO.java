package gr.knowledge.internship.introduction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProductDTO {
	private EmployeeDTO employee;
	private Long id;
	private ProductDTO product;
}
