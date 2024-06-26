package gr.knowledge.internship.introduction.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.knowledge.internship.introduction.dto.EmployeeProductDTO;
import gr.knowledge.internship.introduction.dto.EmployeeProductMapKeyDTO;
import gr.knowledge.internship.introduction.dto.ProductDTO;
import gr.knowledge.internship.introduction.service.EmployeeProductService;

@RestController
@RequestMapping(value = "/employee-products")
public class EmployeeProductController {

	@Autowired
	private EmployeeProductService employeeProductService;

	@DeleteMapping("/employee-product-deletion")
	public void deleteEmployeeProduct(@RequestBody EmployeeProductDTO employeeProductDTO) {
		employeeProductService.deleteEmployeeProduct(employeeProductDTO);
	}

	@GetMapping("/company-products/{companyId}")
	public Map<EmployeeProductMapKeyDTO, List<ProductDTO>> getCompanyProducts(@PathVariable Long companyId) {
		return employeeProductService.getCompanyProducts(companyId);
	}

	@GetMapping
	public List<EmployeeProductDTO> getEmployeeProduct() {
		return employeeProductService.getAllEmployeeProducts();
	}

	@GetMapping("/{employeeProductId}")
	public EmployeeProductDTO getEmployeeProductById(@PathVariable Long employeeProductId) {
		return employeeProductService.getEmployeeProductById(employeeProductId);
	}

	@PostMapping("/employee-product-save")
	public EmployeeProductDTO saveEmployeeProduct(@RequestBody EmployeeProductDTO employeeProductDTO) {
		return employeeProductService.saveEmployeeProduct(employeeProductDTO);
	}

	@PutMapping("/employee-product-update")
	public EmployeeProductDTO updateEmployeeProduct(@RequestBody EmployeeProductDTO employeeProductDTO) {
		return employeeProductService.updateEmployeeProduct(employeeProductDTO);
	}

}
