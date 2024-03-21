package gr.knowledge.internship.introduction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	@Column(name = "id", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
	@SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 50)
	private Long id;

	@Column(name = "name", length = 255, nullable = false)
	private String name;

	@Column(name = "description", length = 1000, nullable = false)
	private String description;

	@Column(name = "barcode", length = 255, nullable = false)
	private String barcode;
}
