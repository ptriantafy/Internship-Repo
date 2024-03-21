package gr.knowledge.internship.introduction.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee {
	@Column(name = "id", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_employee")
	@SequenceGenerator(name = "seq_employee", sequenceName = "seq_employee", allocationSize = 50)
	private Long id;

	@Column(name = "name", length = 255, nullable = false)
	private String name;

	@Column(name = "surname", length = 255, nullable = false)
	private String surname;

	@Column(name = "email", length = 255, nullable = false)
	private String email;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "vacation_days")
	private int vacationDays;

	@Column(name = "salary", nullable = false)
	private BigDecimal salary;

	@Column(name = "employment_type", length = 20, nullable = false)
	private String employmentType;

	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;
}
