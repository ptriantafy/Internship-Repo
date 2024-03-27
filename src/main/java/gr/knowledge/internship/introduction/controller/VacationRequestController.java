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

	@GetMapping
	public List<VacationRequestDTO> getVacationRequests() {
		return vacationRequestService.getAllVacationRequests();
	}

	@GetMapping("/{vacationRequestId}")
	public VacationRequestDTO getVacationRequestById(@PathVariable int vacationRequestId) {
		return vacationRequestService.getVacationRequestById(vacationRequestId);
	}

	@PutMapping("/vacation-request-update")
	public VacationRequestDTO updateVacationRequest(@RequestBody VacationRequestDTO vacationRequestDTO) {
		return vacationRequestService.updateVacationRequest(vacationRequestDTO);
	}

	@PostMapping("/vacation-request-save")
	public VacationRequestHolidayDTO saveVacationRequest(@RequestBody VacationRequestHolidayDTO vacationRequestHolidayDTO) {
		return vacationRequestService.saveVacationRequest(vacationRequestHolidayDTO);
	}

	@DeleteMapping("/vacation-request-deletion")
	public boolean deleteVacationRequest(@RequestBody VacationRequestDTO VacationRequestDTO) {
		return vacationRequestService.deleteVacationRequest(VacationRequestDTO);
	}
}
