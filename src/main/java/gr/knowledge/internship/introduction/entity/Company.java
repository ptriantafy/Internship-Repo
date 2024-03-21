package gr.knowledge.internship.introduction.entity;

import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company")
public class Company {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_company")
	@SequenceGenerator(name = "seq_company", sequenceName = "seq_company", allocationSize = 50)
	private Long id;

	@Column(name = "name", length = 255, nullable = false)
	private String name;

	@Column(name = "address", length = 255, nullable = false)
	private String address;

	@Column(name = "phone", length = 20, nullable = false)
	private String phone;
}
