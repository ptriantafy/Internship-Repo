package gr.knowledge.internship.introduction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.knowledge.internship.introduction.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	public List<Employee> findByCompanyId(int companyId);
}
