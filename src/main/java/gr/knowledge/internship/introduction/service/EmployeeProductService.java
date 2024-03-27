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

    /**
     * Deletes an employee product.
     * @param employeeProductDTO the employee product to be deleted
     * @return true if the employee product was successfully deleted, false otherwise
     */
    public void deleteEmployeeProduct(EmployeeProductDTO employeeProductDTO) {
        employeeProductRepository.delete(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
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

    private Map<Long, String> extractEmployeeInfoMapFromList(List<EmployeeProductDTO> employeeProductsList) {
        Map<Long, String> employeeIdNameMap = new HashMap<>();
        for (EmployeeProductDTO employeeProduct : employeeProductsList) {
            EmployeeDTO currEmployee = employeeProduct.getEmployee();
            employeeIdNameMap.put(currEmployee.getId(), currEmployee.getName().concat(currEmployee.getSurname()));
        }
        return employeeIdNameMap;
    }

    private List<ProductDTO> extractProductsOfEmployee(Long employeeId, List<EmployeeProductDTO> unformattedProductList) {
        List<ProductDTO> extractedProducts = new ArrayList<>();
        for (EmployeeProductDTO currEmployeeProductDTO : unformattedProductList) {
            if (currEmployeeProductDTO.getEmployee().getId().equals(employeeId)) {
				extractedProducts.add(currEmployeeProductDTO.getProduct());
			}
        }
        return extractedProducts;
    }

    /**
     * Retrieves all employee products.
     * @return a list of all employee products
     */
    @Transactional(readOnly = true)
    public List<EmployeeProductDTO> getAllEmployeeProducts() {
        List<EmployeeProduct> employeeProducts = employeeProductRepository.findAll();
        return modelMapper.map(employeeProducts, new TypeToken<List<EmployeeProductDTO>>() {}.getType());
    }

    /**
     * Retrieves company products.
     * @param companyId the ID of the company
     * @return a map containing employee names as keys and lists of products as values
     */
    @Transactional(readOnly = true)
    public Map<String, List<ProductDTO>> getCompanyProducts(Long companyId) {
        Map<String, List<ProductDTO>> outMap = new HashMap<>();
        List<EmployeeProductDTO> unformattedProductList = extractCompanyProducts(companyId);
        Map<Long, String> employeeIdNameMap = extractEmployeeInfoMapFromList(unformattedProductList);

        for (Map.Entry<Long, String> currEntry : employeeIdNameMap.entrySet()) {
            if (outMap.containsKey(currEntry.getValue())) {
				currEntry.setValue(currEntry.getValue().concat(" "));
			}
            outMap.put(currEntry.getValue(), extractProductsOfEmployee(currEntry.getKey(), unformattedProductList));
        }

        return outMap;
    }

    /**
     * Retrieves an employee product by its ID.
     * @param employeeProductId the ID of the employee product to retrieve
     * @return the employee product with the specified ID
     */
    @Transactional(readOnly = true)
    public EmployeeProductDTO getEmployeeProductById(Long employeeProductId) {
        EmployeeProduct employeeProduct = employeeProductRepository.getReferenceById(employeeProductId);
        return modelMapper.map(employeeProduct, EmployeeProductDTO.class);
    }

    /**
     * Retrieves employee products by employee ID.
     * @param employeeId the ID of the employee
     * @return a list of employee products associated with the specified employee
     */
    @Transactional(readOnly = true)
    public List<EmployeeProductDTO> getEmployeProductsByEmployeeId(Long employeeId) {
        List<EmployeeProduct> employeeProductDTOs = employeeProductRepository.getEmployeeProductByEmployeeId(employeeId);
        return modelMapper.map(employeeProductDTOs, new TypeToken<List<EmployeeProductDTO>>() {}.getType());
    }

    /**
     * Saves an employee product.
     * @param employeeProductDTO the employee product to be saved
     * @return the saved employee product
     */
    public EmployeeProductDTO saveEmployeeProduct(EmployeeProductDTO employeeProductDTO) {
        employeeProductRepository.save(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
        return employeeProductDTO;
    }

    /**
     * Updates an employee product.
     * @param employeeProductDTO the employee product to be updated
     * @return the updated employee product
     */
    public EmployeeProductDTO updateEmployeeProduct(EmployeeProductDTO employeeProductDTO) {
        employeeProductRepository.save(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
        return employeeProductDTO;
    }
}
