package gr.knowledge.internship.introduction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.knowledge.internship.introduction.dto.EmployeeDTO;
import gr.knowledge.internship.introduction.dto.EmployeeProductDTO;
import gr.knowledge.internship.introduction.dto.ProductDTO;
import gr.knowledge.internship.introduction.entity.EmployeeProduct;
import gr.knowledge.internship.introduction.repository.EmployeeProductRepository;

@Service
@Transactional
public class EmployeeProductService {

	@Autowired
	private EmployeeProductRepository employeeProductRepository;

	@Autowired
	private ModelMapper modelMapper;

	public EmployeeProductDTO saveEmployeeProduct(EmployeeProductDTO employeeProductDTO) {
		employeeProductRepository.save(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
		return employeeProductDTO;
	}

	@Transactional(readOnly = true)
	public List<EmployeeProductDTO> getAllEmployeeProducts() {
		List<EmployeeProduct> employeeProducts = employeeProductRepository.findAll();
		return modelMapper.map(employeeProducts, new TypeToken<List<EmployeeProductDTO>>() {
		}.getType());
	}

	@Transactional(readOnly = true)
	public EmployeeProductDTO getEmployeeProductById(Long employeeProductId) {
		EmployeeProduct employeeProduct = employeeProductRepository.getReferenceById(employeeProductId);
		return modelMapper.map(employeeProduct, EmployeeProductDTO.class);
	}

	@Transactional(readOnly = true)
	public List<EmployeeProductDTO> getEmployeProductsByEmployeeId(Long employeeId) {
		List<EmployeeProduct> employeeProductDTOs = employeeProductRepository
				.getEmployeeProductByEmployeeId(employeeId);
		return modelMapper.map(employeeProductDTOs, new TypeToken<List<EmployeeProductDTO>>() {
		}.getType());
	}

	public EmployeeProductDTO updateEmployeeProduct(EmployeeProductDTO employeeProductDTO) {
		employeeProductRepository.save(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
		return employeeProductDTO;
	}

	public boolean deleteEmployeeProduct(EmployeeProductDTO employeeProductDTO) {
		employeeProductRepository.delete(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
		return true;
	}

	@Transactional(readOnly = true)
	public Map<String, List<ProductDTO>> getCompanyProducts(Long companyId) {
		Map<String, List<ProductDTO>> outMap = new HashMap<String, List<ProductDTO>>();

		List<EmployeeProductDTO> unformatedProductList = this.extractCompanyProducts(companyId);
		Map<Long, String> employeeIdNameMap = this.extractEmployeeInfoMapFromList(unformatedProductList);

		for (Map.Entry<Long, String> currEntry : employeeIdNameMap.entrySet()) {
//			on duplicate key add a whitespace
			if (outMap.containsKey(currEntry.getValue()))
				currEntry.setValue(currEntry.getValue().concat(" "));
			outMap.put(currEntry.getValue(), this.extractProductsOfEmployee(currEntry.getKey(), unformatedProductList));
		}

		return outMap;
	}

	private List<EmployeeProductDTO> extractCompanyProducts(Long companyId) {
		List<EmployeeProductDTO> allEmployeeProductDTOs = this.getAllEmployeeProducts();
		List<EmployeeProductDTO> extractedDTO = new ArrayList<EmployeeProductDTO>();
		for (EmployeeProductDTO currDTO : allEmployeeProductDTOs) {
			if (currDTO.getEmployee().getCompany().getId().equals(companyId))
				extractedDTO.add(currDTO);
		}
		return extractedDTO;
	}

	private List<ProductDTO> extractProductsOfEmployee(Long employeeId,
			List<EmployeeProductDTO> unformatedProductList) {
		List<ProductDTO> extractedProcuts = new ArrayList<ProductDTO>();
		for (EmployeeProductDTO currEmployeeProductDTO : unformatedProductList) {
			if (currEmployeeProductDTO.getEmployee().getId().equals(employeeId))
				extractedProcuts.add(currEmployeeProductDTO.getProduct());
		}
		return extractedProcuts;
	}

	private Map<Long, String> extractEmployeeInfoMapFromList(List<EmployeeProductDTO> employeeProductsList) {
		Map<Long, String> employeeIdNameMap = new HashMap<Long, String>();
		for (EmployeeProductDTO employeeProduct : employeeProductsList) {
			EmployeeDTO currEmployee = employeeProduct.getEmployee();
			employeeIdNameMap.put(currEmployee.getId(), currEmployee.getName().concat(currEmployee.getSurname()));
		}
		return employeeIdNameMap;
	}
}
