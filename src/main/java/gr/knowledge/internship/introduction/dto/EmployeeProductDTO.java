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
	private Long id; 
	private EmployeeDTO employee;
	private ProductDTO product;
}
