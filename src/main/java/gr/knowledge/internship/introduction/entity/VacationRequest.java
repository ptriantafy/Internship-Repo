package gr.knowledge.internship.introduction.entity;

import java.time.LocalDate;

import gr.knowledge.internship.introduction.enums.VacationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VacationRequest {
	@Column(name = "days", nullable = false)
	private int days;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacation_request_seq")
	@SequenceGenerator(name = "vacation_request_seq", sequenceName = "vacation_request_seq", allocationSize = 50)
	private Long id;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 20, nullable = false)
	private VacationStatus status;

}
