package gr.knowledge.internship.introduction.parameterobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyBonusParameter {
	private int companyId;
	private String season;
}
