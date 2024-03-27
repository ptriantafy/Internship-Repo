package gr.knowledge.internship.introduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.knowledge.internship.introduction.entity.Bonus;

public interface BonusRepository extends JpaRepository<Bonus, Long>{

}
