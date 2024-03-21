package gr.knowledge.internship.introduction.service;

import java.time.temporal.ChronoUnit;
import java.util.List;

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
//		remove holidays from vacation_days
		int days = (int) (vacationRequestHolidayDTO.getStartDate().until(vacationRequestHolidayDTO.getEndDate(),
				ChronoUnit.DAYS) - vacationRequestHolidayDTO.getHolidays() + 1);
		vacationRequestHolidayDTO.setDays(days);
		vacationRequestHolidayDTO.setStatus(VacationStatus.PENDING);
		if (vacationRequestHolidayDTO.getDays() > vacationRequestHolidayDTO.getEmployee().getVacationDays()) {
//		not enough vacation_days
			vacationRequestHolidayDTO.setStatus(VacationStatus.REJECTED);
		}
		vacationRequestRepository
				.save(new VacationRequest(vacationRequestHolidayDTO.getId(), vacationRequestHolidayDTO.getEmployee(),
						vacationRequestHolidayDTO.getStartDate(), vacationRequestHolidayDTO.getEndDate(),
						vacationRequestHolidayDTO.getStatus(), vacationRequestHolidayDTO.getDays()));
		return vacationRequestHolidayDTO;
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

//	TODO less spaghetti
	public VacationRequestDTO updateVacationRequest(VacationRequestDTO vacationRequestDTO) {
//		save status temporarily
		VacationRequestDTO dbData = modelMapper.map(
				vacationRequestRepository.findById((int) (long) vacationRequestDTO.getId()).get(),
				VacationRequestDTO.class);
//		if accepting/rejecting a pending request
		try {
			if (!vacationRequestDTO.getStatus().equals(dbData.getStatus())) {
//			if accepting, try removing available days from employee
				if (vacationRequestDTO.getStatus().equals(VacationStatus.APPROVED)) {
					employeeService.removeVacationDays((int) (long) dbData.getEmployee().getId(), dbData.getDays());
				}
				dbData.setStatus(vacationRequestDTO.getStatus().toString());
				vacationRequestDTO = dbData;
			}
		} catch (NullPointerException npe) {
			return null;
		}
		vacationRequestRepository.save(modelMapper.map(vacationRequestDTO, VacationRequest.class));
		return vacationRequestDTO;

	}

	public boolean deleteVacationRequest(VacationRequestDTO vacationRequestDTO) {
		vacationRequestRepository.delete(modelMapper.map(vacationRequestDTO, VacationRequest.class));
		return true;
	}
}
