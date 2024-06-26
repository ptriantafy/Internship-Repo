package gr.knowledge.internship.introduction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.knowledge.internship.introduction.dto.VacationRequestDTO;
import gr.knowledge.internship.introduction.dto.VacationRequestHolidayDTO;
import gr.knowledge.internship.introduction.service.VacationRequestService;

@RestController
@RequestMapping(value = "/vacation-requests")
public class VacationRequestController {

	@Autowired
	private VacationRequestService vacationRequestService;

	@DeleteMapping("/vacation-request-deletion")
	public void deleteVacationRequest(@RequestBody VacationRequestDTO VacationRequestDTO) {
		vacationRequestService.deleteVacationRequest(VacationRequestDTO);
	}

	@GetMapping("/{vacationRequestId}")
	public VacationRequestDTO getVacationRequestById(@PathVariable Long vacationRequestId) {
		return vacationRequestService.getVacationRequestById(vacationRequestId);
	}

	@GetMapping
	public List<VacationRequestDTO> getVacationRequests() {
		return vacationRequestService.getAllVacationRequests();
	}

	@PostMapping("/vacation-request-save")
	public VacationRequestHolidayDTO saveVacationRequest(
			@RequestBody VacationRequestHolidayDTO vacationRequestHolidayDTO) {
		return vacationRequestService.saveVacationRequest(vacationRequestHolidayDTO);
	}

	@PutMapping("/vacation-request-update")
	public VacationRequestDTO updateVacationRequest(@RequestBody VacationRequestDTO vacationRequestDTO) {
		return vacationRequestService.updateVacationRequest(vacationRequestDTO);
	}
}
