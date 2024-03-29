package gr.knowledge.internship.introduction.service;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.knowledge.internship.introduction.dto.CompanyDTO;
import gr.knowledge.internship.introduction.dto.EmployeeDTO;
import gr.knowledge.internship.introduction.entity.Company;
import gr.knowledge.internship.introduction.repository.CompanyRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Deletes a company.
	 *
	 * @param companyDTO the company to be deleted
	 * @return true
	 */
	public void deleteCompany(CompanyDTO companyDTO) {
		log.debug("Requested delete of company: " + companyDTO.toString());
		companyRepository.delete(modelMapper.map(companyDTO, Company.class));
	}

	/**
	 * Retrieves all companies.
	 *
	 * @return a list of all companies
	 */
	@Transactional(readOnly = true)
	public List<CompanyDTO> getAllCompanies() {
		List<Company> companyList = companyRepository.findAll();
		log.debug("Requested all companies. Returned " + companyList.size() + " items.");
		return modelMapper.map(companyList, new TypeToken<List<CompanyDTO>>() {
		}.getType());
	}

	/**
	 * Retrieves a company by its ID.
	 *
	 * @param companyId the ID of the company to retrieve
	 * @return the company with the specified ID
	 */
	@Transactional(readOnly = true)
	public CompanyDTO getCompanyById(Long companyId) {
		log.debug("Requested company with ID: " + companyId);
		Company company = companyRepository.getReferenceById(companyId);
		return modelMapper.map(company, CompanyDTO.class);
	}

	/**
	 * Calculates the monthly expenses for a company.
	 *
	 * @param companyId the ID of the company
	 * @return the total monthly expenses for the company
	 */
	@Transactional(readOnly = true)
	public BigDecimal getMonthlyExpenses(Long companyId) {
		List<EmployeeDTO> employeesDTO = modelMapper.map(employeeService.getCompanyEmployees(companyId),
				new TypeToken<List<EmployeeDTO>>() {
				}.getType());
		log.debug(
				"Requested expenses for company with ID: " + companyId + " and " + employeesDTO.size() + " employees.");
		return employeesDTO.stream().map(EmployeeDTO::getSalary).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/**
	 * Saves a company.
	 *
	 * @param companyDTO the company to be saved
	 * @return the saved company
	 */
	public CompanyDTO saveCompany(CompanyDTO companyDTO) {
		log.debug("Saved company: " + companyDTO.toString());
		companyRepository.save(modelMapper.map(companyDTO, Company.class));
		return companyDTO;
	}

	/**
	 * Updates a company.
	 *
	 * @param companyDTO the company to be updated
	 * @return the updated company
	 */
	public CompanyDTO updateCompany(CompanyDTO companyDTO) {
		log.debug("Updated company: " + companyDTO.toString());
		companyRepository.save(modelMapper.map(companyDTO, Company.class));
		return companyDTO;
	}
}
