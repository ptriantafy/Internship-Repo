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

import gr.knowledge.internship.introduction.dto.EmployeeDTO;
import gr.knowledge.internship.introduction.service.EmployeeService;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@DeleteMapping("/employee-deletion")
	public void deleteEmployee(@RequestBody EmployeeDTO employeeDTO) {
		employeeService.deleteEmployee(employeeDTO);
	}

	@GetMapping
	public List<EmployeeDTO> getEmployee() {
		return employeeService.getAllEmployees();
	}

	@GetMapping("/{employeeId}")
	public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
		return employeeService.getEmployeeById(employeeId);
	}

	@PostMapping("/employee-save")
	public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return employeeService.saveEmployee(employeeDTO);
	}

	@PutMapping("/employee-update")
	public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return employeeService.updateEmployee(employeeDTO);
	}
}
