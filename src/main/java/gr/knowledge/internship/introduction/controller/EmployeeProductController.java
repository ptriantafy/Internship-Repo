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
import gr.knowledge.internship.introduction.dto.ProductDTO;
import gr.knowledge.internship.introduction.service.EmployeeProductService;

@RestController
@RequestMapping(value = "/employee-products")
public class EmployeeProductController {

    @Autowired
    private EmployeeProductService employeeProductService;

    @GetMapping
    public List<EmployeeProductDTO> getEmployeeProduct(){
        return employeeProductService.getAllEmployeeProducts();
    }

    @GetMapping("/company-products/{companyId}")
    public Map<String, List<ProductDTO>> getCompanyProducts(@PathVariable int companyId){
    	return employeeProductService.getCompanyProducts(companyId);
    }
    
    @GetMapping("/{employeeProductId}")
    public EmployeeProductDTO getEmployeeProductById(@PathVariable int employeeProductId) {
    	return employeeProductService.getEmployeeProductById(employeeProductId);
    }
    @PostMapping("/employee-product-save")
    public EmployeeProductDTO saveEmployeeProduct(@RequestBody EmployeeProductDTO employeeProductDTO){
        return employeeProductService.saveEmployeeProduct(employeeProductDTO);
    }

    @PutMapping("/employee-product-update")
    public EmployeeProductDTO updateEmployeeProduct(@RequestBody EmployeeProductDTO employeeProductDTO){
        return employeeProductService.updateEmployeeProduct(employeeProductDTO);
    }

    @DeleteMapping("/employee-product-deletion")
    public boolean deleteEmployeeProduct(@RequestBody EmployeeProductDTO employeeProductDTO){
        return employeeProductService.deleteEmployeeProduct(employeeProductDTO);
    }
    

}
