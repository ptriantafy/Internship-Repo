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

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ModelMapper modelMapper;

	public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
		employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));
		return employeeDTO;
	}

	@Transactional(readOnly = true)
	public List<EmployeeDTO> getAllEmployees() {
		List<Employee> employeeList = employeeRepository.findAll();
		return modelMapper.map(employeeList, new TypeToken<List<EmployeeDTO>>() {
		}.getType());
	}

	@Transactional(readOnly = true)
	public EmployeeDTO getEmployeeById(Long employeeId) {
		Employee employee = employeeRepository.getReferenceById(employeeId);
		return modelMapper.map(employee, EmployeeDTO.class);
	}

	public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
		employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));
		return employeeDTO;
	}

	public EmployeeDTO removeVacationDays(Long employeeId, int days) throws IllegalArgumentException {
		EmployeeDTO employeeDTO = this.getEmployeeById(employeeId);
		if (employeeDTO.getVacationDays() >= days) {
			employeeDTO.setVacationDays(employeeDTO.getVacationDays() - days);
			return employeeDTO;
		}
		throw new IllegalArgumentException("Employee does not have enough Vacation Days");
	}

	@Transactional(readOnly = true)
	public List<EmployeeDTO> getCompanyEmployees(int companyId) {
		return modelMapper.map(employeeRepository.findByCompanyId(companyId), new TypeToken<List<EmployeeDTO>>() {
		}.getType());
	}

	public boolean deleteEmployee(EmployeeDTO employeeDTO) {
		employeeRepository.delete(modelMapper.map(employeeDTO, Employee.class));
		return true;
	}

}