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
import org.springframework.transaction.annotation.Transactional;

import gr.knowledge.internship.introduction.dto.VacationRequestDTO;
import gr.knowledge.internship.introduction.dto.VacationRequestHolidayDTO;
import gr.knowledge.internship.introduction.entity.VacationRequest;
import gr.knowledge.internship.introduction.enums.VacationStatus;
import gr.knowledge.internship.introduction.repository.VacationRequestRepository;

@Service
@Transactional
public class VacationRequestService {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private ModelMapper modelMapper;

    private VacationRequestDTO approveVacationRequest(VacationRequestDTO requestDTO) {
        try {
            this.employeeService.removeVacationDays(requestDTO.getEmployee().getId(), requestDTO.getDays());
        } catch (IllegalArgumentException iae) {
            requestDTO.setStatus(VacationStatus.REJECTED);
        }
        return requestDTO;
    }

    /**
     * Checks if a vacation request should be automatically rejected.
     * @param vacationRequestHolidayDTO the vacation request
     * @return true if the request should be automatically rejected, false otherwise
     */
    public boolean automaticReject(VacationRequestHolidayDTO vacationRequestHolidayDTO) {
        int days = (int) (ChronoUnit.DAYS.between(vacationRequestHolidayDTO.getStartDate(),
                vacationRequestHolidayDTO.getStartDate()) - vacationRequestHolidayDTO.getHolidays());
        vacationRequestHolidayDTO.setDays(days);
        return vacationRequestHolidayDTO.getDays() > vacationRequestHolidayDTO.getEmployee().getVacationDays();
    }

    /**
     * Deletes a vacation request.
     * @param vacationRequestDTO the vacation request to be deleted
     * @return true
     */
    public boolean deleteVacationRequest(VacationRequestDTO vacationRequestDTO) {
        vacationRequestRepository.delete(modelMapper.map(vacationRequestDTO, VacationRequest.class));
        return true;
    }

    /**
     * Retrieves all vacation requests.
     * @return a list of all vacation requests
     */
    @Transactional(readOnly = true)
    public List<VacationRequestDTO> getAllVacationRequests() {
        List<VacationRequest> vacationRequests = vacationRequestRepository.findAll();
        return modelMapper.map(vacationRequests, new TypeToken<List<VacationRequestDTO>>() {}.getType());
    }

    /**
     * Retrieves a vacation request by its ID.
     * @param vacationRequestId the ID of the vacation request to retrieve
     * @return the vacation request with the specified ID
     */
    @Transactional(readOnly = true)
    public VacationRequestDTO getVacationRequestById(Long vacationRequestId) {
        VacationRequest vacationRequest = vacationRequestRepository.getReferenceById(vacationRequestId);
        return modelMapper.map(vacationRequest, VacationRequestDTO.class);
    }

    private VacationRequestDTO pendingVacationRequestDTO(VacationRequestDTO requestDTO) {
        return requestDTO;
    }

    private VacationRequestDTO rejectVacationRequestDTO(VacationRequestDTO requestDTO) {
        return requestDTO;
    }

    /**
     * Saves a vacation request.
     * @param vacationRequestHolidayDTO the vacation request to be saved
     * @return the saved vacation request
     */
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

    /**
     * Updates a vacation request.
     * @param vacationRequestDTO the vacation request to be updated
     * @return the updated vacation request
     */
    public VacationRequestDTO updateVacationRequest(VacationRequestDTO vacationRequestDTO) {
        Map<VacationStatus, Function<VacationRequestDTO, VacationRequestDTO>> responseMap = new HashMap<>();
        responseMap.put(VacationStatus.APPROVED, this::approveVacationRequest);
        responseMap.put(VacationStatus.PENDING, this::pendingVacationRequestDTO);
        responseMap.put(VacationStatus.REJECTED, this::rejectVacationRequestDTO);
        return responseMap.get(vacationRequestDTO.getStatus()).apply(vacationRequestDTO);
    }
}
