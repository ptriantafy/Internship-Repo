package gr.knowledge.internship.introduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.knowledge.internship.introduction.entity.EmployeeProduct;

public interface EmployeeProductRepository extends JpaRepository<EmployeeProduct, Integer>{

}
