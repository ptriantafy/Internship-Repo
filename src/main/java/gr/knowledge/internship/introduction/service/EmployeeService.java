package gr.knowledge.internship.introduction.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.knowledge.internship.introduction.dto.EmployeeDTO;
import gr.knowledge.internship.introduction.entity.Employee;
import gr.knowledge.internship.introduction.repository.EmployeeRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Deletes an employee.
	 *
	 * @param employeeDTO the employee to be deleted
	 * @return true
	 */
	public void deleteEmployee(EmployeeDTO employeeDTO) {
		log.debug("Requested Delete of employee: " + employeeDTO.toString());
		employeeRepository.delete(modelMapper.map(employeeDTO, Employee.class));
	}

	/**
	 * Retrieves all employees.
	 *
	 * @return a list of all employees
	 */
	@Transactional(readOnly = true)
	public List<EmployeeDTO> getAllEmployees() {
		List<Employee> employeeList = employeeRepository.findAll();
		log.debug("Requested all employees. Returned %d items.", employeeList.size());
		return modelMapper.map(employeeList, new TypeToken<List<EmployeeDTO>>() {
		}.getType());
	}

	/**
	 * Retrieves employees of a company.
	 *
	 * @param companyId the ID of the company
	 * @return a list of employees belonging to the specified company
	 */
	@Transactional(readOnly = true)
	public List<EmployeeDTO> getCompanyEmployees(int companyId) {
		log.debug("Asked for all employees of company with id: %d", companyId);
		return modelMapper.map(employeeRepository.findByCompanyId(companyId), new TypeToken<List<EmployeeDTO>>() {
		}.getType());
	}

	/**
	 * Retrieves an employee by its ID.
	 *
	 * @param employeeId the ID of the employee to retrieve
	 * @return the employee with the specified ID
	 */
	@Transactional(readOnly = true)
	public EmployeeDTO getEmployeeById(Long employeeId) {
		Employee employee = employeeRepository.getReferenceById(employeeId);
		log.debug("Asked for employee with id: %d", employeeId);
		return modelMapper.map(employee, EmployeeDTO.class);
	}

	/**
	 * Removes vacation days from an employee.
	 *
	 * @param employeeId the ID of the employee
	 * @param days       the number of vacation days to remove
	 * @return the updated employee
	 * @throws IllegalArgumentException if the employee does not have enough
	 *                                  vacation days
	 */
	public EmployeeDTO removeVacationDays(Long employeeId, int days) throws IllegalArgumentException {
		EmployeeDTO employeeDTO = getEmployeeById(employeeId);
		if (employeeDTO.getVacationDays() >= days) {
			employeeDTO.setVacationDays(employeeDTO.getVacationDays() - days);
			return employeeDTO;
		}
		throw new IllegalArgumentException("Employee does not have enough Vacation Days");
	}

	/**
	 * Saves an employee.
	 *
	 * @param employeeDTO the employee to be saved
	 * @return the saved employee
	 */
	public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
		log.debug("Saved Employee: " + employeeDTO.toString());
		employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));
		return employeeDTO;
	}

	/**
	 * Updates an employee.
	 *
	 * @param employeeDTO the employee to be updated
	 * @return the updated employee
	 */
	public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
		log.debug("Updated Employee: " + employeeDTO.toString());
		employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));
		return employeeDTO;
	}
}
