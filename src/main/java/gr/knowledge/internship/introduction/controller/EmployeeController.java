package gr.knowledge.internship.introduction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import gr.knowledge.internship.introduction.dto.EmployeeDTO;
import gr.knowledge.internship.introduction.service.EmployeeService;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDTO> getEmployee(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId){
        return employeeService.getEmployeeById(employeeId);
    }
    
    @PostMapping("/employee-save")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeService.saveEmployee(employeeDTO);
    }

    @PutMapping("/employee-update")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeService.updateEmployee(employeeDTO);
    }

    @DeleteMapping("/employee-deletion")
    public boolean deleteEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeService.deleteEmployee(employeeDTO);
    }
}
