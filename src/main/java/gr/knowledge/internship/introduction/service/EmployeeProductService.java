package gr.knowledge.internship.introduction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.knowledge.internship.introduction.dto.EmployeeDTO;
import gr.knowledge.internship.introduction.dto.EmployeeProductDTO;
import gr.knowledge.internship.introduction.dto.EmployeeProductMapKeyDTO;
import gr.knowledge.internship.introduction.dto.ProductDTO;
import gr.knowledge.internship.introduction.entity.EmployeeProduct;
import gr.knowledge.internship.introduction.repository.EmployeeProductRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class EmployeeProductService {

	@Autowired
	private EmployeeProductRepository employeeProductRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Deletes an employee product.
	 *
	 * @param employeeProductDTO the employee product to be deleted
	 * @return true if the employee product was successfully deleted, false
	 *         otherwise
	 */
	public void deleteEmployeeProduct(EmployeeProductDTO employeeProductDTO) {
		log.debug("Requested deleting of employee-prodcut: " + employeeProductDTO.toString());
		employeeProductRepository.delete(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
	}

	/**
	 * Retrieves all employee products.
	 *
	 * @return a list of all employee products
	 */
	@Transactional(readOnly = true)
	public List<EmployeeProductDTO> getAllEmployeeProducts() {
		List<EmployeeProduct> employeeProducts = employeeProductRepository.findAll();
		log.debug("Requested all employee-products. Returned " + employeeProducts.size() + " items.");
		return modelMapper.map(employeeProducts, new TypeToken<List<EmployeeProductDTO>>() {
		}.getType());
	}

	/**
	 * Retrieves company products.
	 *
	 * @param companyId the ID of the company
	 * @return a map containing employee names as keys and lists of products as
	 *         values
	 */
	@Transactional(readOnly = true)
	public Map<EmployeeProductMapKeyDTO, List<ProductDTO>> getCompanyProducts(Long companyId) {
		Map<EmployeeProductMapKeyDTO, List<ProductDTO>> outMap = new HashMap<>();
		List<EmployeeProductDTO> unformattedProductList = extractCompanyProducts(companyId);
		List<EmployeeDTO> companyEmployeesList = employeeService.getEmployeesOfCompany(companyId);

		for (EmployeeDTO currEmployee : companyEmployeesList) {
			EmployeeProductMapKeyDTO mapKey = new EmployeeProductMapKeyDTO();
			mapKey.fromEmployee(currEmployee);
			outMap.put(mapKey, extractProductsOfEmployee(currEmployee.getId(), unformattedProductList));
		}
		log.debug("Returned all company products of company with id:" + companyId + ". Entries: " + outMap.size());
		return outMap;
	}

	/**
	 * Retrieves an employee product by its ID.
	 *
	 * @param employeeProductId the ID of the employee product to retrieve
	 * @return the employee product with the specified ID
	 */
	@Transactional(readOnly = true)
	public EmployeeProductDTO getEmployeeProductById(Long employeeProductId) {
		EmployeeProduct employeeProduct = employeeProductRepository.getReferenceById(employeeProductId);
		log.debug("Requested employee-product with id: " + employeeProductId);
		return modelMapper.map(employeeProduct, EmployeeProductDTO.class);
	}

	/**
	 * Saves an employee product.
	 *
	 * @param employeeProductDTO the employee product to be saved
	 * @return the saved employee product
	 */
	public EmployeeProductDTO saveEmployeeProduct(EmployeeProductDTO employeeProductDTO) {
		employeeProductRepository.save(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
		return employeeProductDTO;
	}

	/**
	 * Updates an employee product.
	 *
	 * @param employeeProductDTO the employee product to be updated
	 * @return the updated employee product
	 */
	public EmployeeProductDTO updateEmployeeProduct(EmployeeProductDTO employeeProductDTO) {
		employeeProductRepository.save(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
		return employeeProductDTO;
	}

	private List<EmployeeProductDTO> extractCompanyProducts(Long companyId) {
		List<EmployeeProductDTO> allEmployeeProductDTOs = getAllEmployeeProducts();
		List<EmployeeProductDTO> extractedDTO = new ArrayList<>();
		for (EmployeeProductDTO currDTO : allEmployeeProductDTOs) {
			if (currDTO.getEmployee().getCompany().getId().equals(companyId)) {
				extractedDTO.add(currDTO);
			}
		}
		return extractedDTO;
	}

	private List<ProductDTO> extractProductsOfEmployee(Long employeeId,
			List<EmployeeProductDTO> unformattedProductList) {
		List<ProductDTO> extractedProducts = new ArrayList<>();
		for (EmployeeProductDTO currEmployeeProductDTO : unformattedProductList) {
			if (currEmployeeProductDTO.getEmployee().getId().equals(employeeId)) {
				extractedProducts.add(currEmployeeProductDTO.getProduct());
			}
		}
		return extractedProducts;
	}

	/**
	 * Retrieves employee products by employee ID.
	 *
	 * @param employeeId the ID of the employee
	 * @return a list of employee products associated with the specified employee
	 */
	@Transactional(readOnly = true)
	private List<EmployeeProductDTO> getEmployeProductsByEmployeeId(Long employeeId) {
		List<EmployeeProduct> employeeProductDTOs = employeeProductRepository
				.getEmployeeProductByEmployeeId(employeeId);
		return modelMapper.map(employeeProductDTOs, new TypeToken<List<EmployeeProductDTO>>() {
		}.getType());
	}
}
