package gr.knowledge.internship.introduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.knowledge.internship.introduction.entity.VacationRequest;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long>{

}
