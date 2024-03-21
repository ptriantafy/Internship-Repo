package gr.knowledge.internship.introduction.service;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.knowledge.internship.introduction.dto.VacationRequestDTO;
import gr.knowledge.internship.introduction.dto.VacationRequestHolidayDTO;
import gr.knowledge.internship.introduction.entity.VacationRequest;
import gr.knowledge.internship.introduction.entity.VacationStatus;
import gr.knowledge.internship.introduction.repository.VacationRequestRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class VacationRequestService {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	private VacationRequestRepository vacationRequestRepository;

	@Autowired
	private ModelMapper modelMapper;

	public VacationRequestHolidayDTO saveVacationRequest(VacationRequestHolidayDTO vacationRequestHolidayDTO) {
		if (vacationRequestHolidayDTO.getHolidays() < 0) {
			throw new IllegalArgumentException("Holidays cannot be negative");
		}
		if (automaticReject(vacationRequestHolidayDTO)) {
			vacationRequestHolidayDTO.setStatus(VacationStatus.REJECTED);
			vacationRequestRepository.save(modelMapper.map(vacationRequestHolidayDTO, VacationRequest.class));
			return vacationRequestHolidayDTO;
		}
		vacationRequestHolidayDTO.setStatus(VacationStatus.PENDING);
		vacationRequestRepository.save(modelMapper.map(vacationRequestHolidayDTO, VacationRequest.class));
		return vacationRequestHolidayDTO;
	}

	public boolean automaticReject(VacationRequestHolidayDTO vacationRequestHolidayDTO) {
//		remove holidays from vacation_days
		int days = (int) (ChronoUnit.DAYS.between(vacationRequestHolidayDTO.getStartDate(),
				vacationRequestHolidayDTO.getStartDate()) - vacationRequestHolidayDTO.getHolidays());
		vacationRequestHolidayDTO.setDays(days);
		if (vacationRequestHolidayDTO.getDays() > vacationRequestHolidayDTO.getEmployee().getVacationDays()) {
			return true;
		}
		return false;
	}

	public List<VacationRequestDTO> getAllVacationRequests() {
		List<VacationRequest> vacationRequests = vacationRequestRepository.findAll();
		return modelMapper.map(vacationRequests, new TypeToken<List<VacationRequestDTO>>() {
		}.getType());
	}

	public VacationRequestDTO getVacationRequestById(int vacationRequestId) {
		VacationRequest vacationRequest = vacationRequestRepository.getReferenceById(vacationRequestId);
		return modelMapper.map(vacationRequest, VacationRequestDTO.class);
	}

//		suppose only valid input for now
	public VacationRequestDTO updateVacationRequest(VacationRequestDTO vacationRequestDTO) {
		Map<VacationStatus, Function<VacationRequestDTO, VacationRequestDTO>> responseMap = new HashMap<>();
		responseMap.put(VacationStatus.APPROVED, this::approveVacationRequest);
		responseMap.put(VacationStatus.PENDING, this::pendingVacationRequestDTO);
		responseMap.put(VacationStatus.REJECTED, this::rejectVacationRequestDTO);
		vacationRequestDTO = responseMap.get(vacationRequestDTO.getStatus()).apply(vacationRequestDTO);
		return vacationRequestDTO;
	}

	public VacationRequestDTO approveVacationRequest(VacationRequestDTO requestDTO) {
		try {
			this.employeeService.removeVacationDays((int) (long) requestDTO.getEmployee().getId(),
					requestDTO.getDays());
		} catch (IllegalArgumentException iae) {
			requestDTO.setStatus(VacationStatus.REJECTED);
			return requestDTO;
		}
		return requestDTO;
	}

//		temporary method
	public VacationRequestDTO rejectVacationRequestDTO(VacationRequestDTO requestDTO) {
		return requestDTO;
	}

//	temporary method
	public VacationRequestDTO pendingVacationRequestDTO(VacationRequestDTO requestDTO) {
		return requestDTO;
	}

	public boolean deleteVacationRequest(VacationRequestDTO vacationRequestDTO) {
		vacationRequestRepository.delete(modelMapper.map(vacationRequestDTO, VacationRequest.class));
		return true;
	}
}
