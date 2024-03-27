package gr.knowledge.internship.introduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.knowledge.internship.introduction.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
