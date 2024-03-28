package gr.knowledge.internship.introduction.dto;

import gr.knowledge.internship.introduction.entity.VacationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VacationRequestHolidayDTO extends VacationRequest {
	private int holidays;
}
