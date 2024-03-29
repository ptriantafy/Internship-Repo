package gr.knowledge.internship.introduction.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.knowledge.internship.introduction.dto.VacationRequestDTO;
import gr.knowledge.internship.introduction.dto.VacationRequestHolidayDTO;
import gr.knowledge.internship.introduction.entity.VacationRequest;
import gr.knowledge.internship.introduction.enums.VacationStatus;
import gr.knowledge.internship.introduction.repository.VacationRequestRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class VacationRequestService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private VacationRequestRepository vacationRequestRepository;

	@Autowired
	EmployeeService employeeService;

	/**
	 * Deletes a vacation request.
	 *
	 * @param vacationRequestDTO the vacation request to be deleted
	 * @return true
	 */
	public void deleteVacationRequest(VacationRequestDTO vacationRequestDTO) {
		log.debug("Vacation request with ID: %d deleted.", vacationRequestDTO.getId());
		vacationRequestRepository.delete(modelMapper.map(vacationRequestDTO, VacationRequest.class));
	}

	/**
	 * Retrieves all vacation requests.
	 *
	 * @return a list of all vacation requests
	 */
	@Transactional(readOnly = true)
	public List<VacationRequestDTO> getAllVacationRequests() {
		log.debug("Requested all Vacation Requests");
		List<VacationRequest> vacationRequests = vacationRequestRepository.findAll();
		return modelMapper.map(vacationRequests, new TypeToken<List<VacationRequestDTO>>() {
		}.getType());
	}

	/**
	 * Retrieves a vacation request by its ID.
	 *
	 * @param vacationRequestId the ID of the vacation request to retrieve
	 * @return the vacation request with the specified ID
	 */
	@Transactional(readOnly = true)
	public VacationRequestDTO getVacationRequestById(Long vacationRequestId) {
		log.debug("Requested fetch of Vacation Request with ID: %d", vacationRequestId);
		VacationRequest vacationRequest = vacationRequestRepository.getReferenceById(vacationRequestId);
		return modelMapper.map(vacationRequest, VacationRequestDTO.class);
	}

	/**
	 * Saves a vacation request.
	 *
	 * @param vacationRequestHolidayDTO the vacation request to be saved
	 * @return the saved vacation request
	 */
	public VacationRequestHolidayDTO saveVacationRequest(VacationRequestHolidayDTO vacationRequestHolidayDTO) {
		if (vacationRequestHolidayDTO.getHolidays() < 0) {
			log.error("New vacation request with negative holidays");
			throw new IllegalArgumentException("Holidays cannot be negative");
		}
		if (automaticReject(vacationRequestHolidayDTO)) {
			vacationRequestHolidayDTO.setStatus(VacationStatus.REJECTED);
			VacationRequestDTO requestToSave = modelMapper.map(vacationRequestHolidayDTO, VacationRequestDTO.class);
			log.debug("New vacation request automatically rejected");
			vacationRequestRepository.save(modelMapper.map(requestToSave, VacationRequest.class));
			return vacationRequestHolidayDTO;
		}
		vacationRequestHolidayDTO.setStatus(VacationStatus.PENDING);
		VacationRequestDTO requestToSave = modelMapper.map(vacationRequestHolidayDTO, VacationRequestDTO.class);
		log.debug("New vacation request set to pending");
		vacationRequestRepository.save(modelMapper.map(requestToSave, VacationRequest.class));
		return vacationRequestHolidayDTO;
	}

	/**
	 * Updates a vacation request.
	 *
	 * @param vacationRequestDTO the vacation request to be updated
	 * @return the updated vacation request
	 */
	public VacationRequestDTO updateVacationRequest(VacationRequestDTO vacationRequestDTO) {
		Map<VacationStatus, Function<VacationRequestDTO, VacationRequestDTO>> responseMap = new HashMap<>();
		responseMap.put(VacationStatus.APPROVED, this::approveVacationRequest);
		responseMap.put(VacationStatus.PENDING, this::pendingVacationRequestDTO);
		responseMap.put(VacationStatus.REJECTED, this::rejectVacationRequestDTO);
		log.debug("Vacation Request with ID: " + vacationRequestDTO.getId() + "requested to be updated as:"
				+ vacationRequestDTO.toString());
		return responseMap.get(vacationRequestDTO.getStatus()).apply(vacationRequestDTO);
	}

	private VacationRequestDTO approveVacationRequest(VacationRequestDTO requestDTO) {
		VacationRequestDTO requestToAccept = modelMapper
				.map(vacationRequestRepository.getReferenceById(requestDTO.getId()), VacationRequestDTO.class);
		try {
			this.employeeService.removeVacationDays(requestToAccept.getEmployee().getId(), requestToAccept.getDays());
			log.debug("Removed %d vacation days from Employee with ID: " + requestToAccept.getDays(),
					requestToAccept.getEmployee().getId());
		} catch (IllegalArgumentException iae) {
			requestDTO.setStatus(VacationStatus.REJECTED);
			log.debug(
					"Failed to remove %d vacation days from Employee with ID: %d with reason 'Not enough Vacation Days'",
					requestToAccept.getDays(), requestToAccept.getEmployee().getId());
		}
		return requestDTO;
	}

	/**
	 * Checks if a vacation request should be automatically rejected.
	 *
	 * @param vacationRequestHolidayDTO the vacation request
	 * @return true if the request should be automatically rejected, false otherwise
	 */
	private boolean automaticReject(VacationRequestHolidayDTO vacationRequestHolidayDTO) {
		int days = this.getWorkDaysBetween(vacationRequestHolidayDTO.getStartDate(),
				vacationRequestHolidayDTO.getEndDate());
		vacationRequestHolidayDTO.setDays(days - vacationRequestHolidayDTO.getHolidays());
		int employeeDays = employeeService.getEmployeeById(vacationRequestHolidayDTO.getEmployee().getId())
				.getVacationDays();
		log.debug("Request Days: " + vacationRequestHolidayDTO.getDays());
		log.debug("Employee Days: " + employeeDays);
		return vacationRequestHolidayDTO.getDays() > employeeDays;
	}

	private int getWorkDaysBetween(LocalDate start, LocalDate end) {
		if (start.isAfter(end)) {
			log.error("New vacation request with end_date before start_date");
			throw new IllegalArgumentException("End date cannot be earlier than start date");
		}
		int workdays = 0;
		while (start.isBefore(end) || start.isEqual(end)) {
			if (start.getDayOfWeek().equals(DayOfWeek.SATURDAY) || start.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				log.debug(start + "weekend");
				start = start.plus(1, ChronoUnit.DAYS);
				continue;
			}
			log.debug(start + "day");
			start = start.plus(1, ChronoUnit.DAYS);
			workdays = workdays + 1;
		}
		log.debug("Calculated workdays to be: " + workdays);
		log.debug("From: " + start.toString() + " To: " + end.toString());
		return workdays;
	}

	private VacationRequestDTO pendingVacationRequestDTO(VacationRequestDTO requestDTO) {
		return requestDTO;
	}

	private VacationRequestDTO rejectVacationRequestDTO(VacationRequestDTO requestDTO) {
		return requestDTO;
	}
}
