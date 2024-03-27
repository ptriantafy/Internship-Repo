package gr.knowledge.internship.introduction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.knowledge.internship.introduction.entity.EmployeeProduct;

public interface EmployeeProductRepository extends JpaRepository<EmployeeProduct, Long>{
	
	public List<EmployeeProduct> getEmployeeProductByEmployeeId(Long employeeId);
}
