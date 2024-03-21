package gr.knowledge.internship.introduction.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.knowledge.internship.introduction.dto.EmployeeProductDTO;
import gr.knowledge.internship.introduction.dto.ProductDTO;
import gr.knowledge.internship.introduction.entity.EmployeeProduct;
import gr.knowledge.internship.introduction.repository.EmployeeProductRepository;
import jakarta.transaction.Transactional;

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

	public List<EmployeeProductDTO> getAllEmployeeProducts() {
		List<EmployeeProduct> employeeProducts = employeeProductRepository.findAll();
		return modelMapper.map(employeeProducts, new TypeToken<List<EmployeeProductDTO>>() {
		}.getType());
	}

	public EmployeeProductDTO getEmployeeProductById(int employeeProductId) {
		EmployeeProduct employeeProduct = employeeProductRepository.getReferenceById(employeeProductId);
		return modelMapper.map(employeeProduct, EmployeeProductDTO.class);
	}

	public EmployeeProductDTO updateEmployeeProduct(EmployeeProductDTO employeeProductDTO) {
		employeeProductRepository.save(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
		return employeeProductDTO;
	}

	public boolean deleteEmployeeProduct(EmployeeProductDTO employeeProductDTO) {
		employeeProductRepository.delete(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
		return true;
	}

	public Map<String, List<ProductDTO>> getCompanyProducts(int companyId) {
		// get all company employee-products
		Set<EmployeeProductDTO> employeeProductList = modelMapper.map(
				employeeProductRepository.findAll().stream()
						.filter(e -> e.getEmployee().getCompany().getId() == companyId).collect(Collectors.toSet()),
				new TypeToken<Set<EmployeeProductDTO>>() {
				}.getType());

		Map<String, List<ProductDTO>> outMap = employeeProductList.stream()
				.collect(Collectors.groupingBy(
						empProd -> empProd.getEmployee().getName().concat(empProd.getEmployee().getSurname()),
						Collectors.mapping(emplProd -> emplProd.getProduct(), Collectors.toList())));
		return outMap;
	}
}
