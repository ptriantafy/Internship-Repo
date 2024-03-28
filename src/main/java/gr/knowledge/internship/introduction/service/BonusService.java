package gr.knowledge.internship.introduction.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.knowledge.internship.introduction.dto.BonusDTO;
import gr.knowledge.internship.introduction.dto.EmployeeDTO;
import gr.knowledge.internship.introduction.entity.Bonus;
import gr.knowledge.internship.introduction.enums.BonusBySeason;
import gr.knowledge.internship.introduction.filtering.CalculateBonusFilter;
import gr.knowledge.internship.introduction.filtering.CompanyBonusFilter;
import gr.knowledge.internship.introduction.repository.BonusRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class BonusService {

	@Autowired
	private BonusRepository bonusRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Calculates the bonus based on provided parameters.
	 *
	 * @param parameter the parameters for bonus calculation
	 * @return the calculated bonus
	 */
	@Transactional(readOnly = true)
	public BigDecimal calculateBonus(CalculateBonusFilter parameter) {
		parameter.validateInput();
		log.debug("Bonus with parameters: " + parameter.toString() + " calculated");
		return BonusBySeason.resolveOfEnum(parameter.getSeason()).getRate().multiply(parameter.getSalary());
	}

	/**
	 * Creates bonuses for the company based on provided parameters.
	 *
	 * @param companyParameter the parameters for creating company bonuses
	 * @return a list of created bonuses for the company
	 */
	public List<BonusDTO> createCompanyBonus(CompanyBonusFilter companyParameter) {
		companyParameter.validateInput();
		List<EmployeeDTO> employeeList = employeeService.getCompanyEmployees(companyParameter.getCompanyId());
		List<BonusDTO> bonusList = new ArrayList<>();
		for (EmployeeDTO employee : employeeList) {
			bonusList.add(this.companyBonusCreator(employee, companyParameter.getSeason()));
		}
		log.debug("Created new Company Bonus for company: " + companyParameter.getCompanyId() + " and season: "
				+ companyParameter.getSeason() + ". Entries: " + bonusList.size());
		return bonusList;
	}

	/**
	 * Deletes a bonus.
	 *
	 * @param bonusDTO the bonus to be deleted
	 * @return true
	 */
	public void deleteBonus(BonusDTO bonusDTO) {
		bonusRepository.delete(modelMapper.map(bonusDTO, Bonus.class));
		log.debug("Requested delete bonus with id: " + bonusDTO.getId());
	}

	/**
	 * Retrieves all bonuses.
	 *
	 * @return a list of all bonuses
	 */
	@Transactional(readOnly = true)
	public List<BonusDTO> getAllBonus() {
		List<Bonus> bonusList = bonusRepository.findAll();
		log.debug("Get all bonus request returned " + bonusList.size() + " items");
		return modelMapper.map(bonusList, new TypeToken<List<BonusDTO>>() {
		}.getType());
	}

	/**
	 * Retrieves a bonus by its ID.
	 *
	 * @param bonusId the ID of the bonus to retrieve
	 * @return the bonus with the specified ID
	 */
	@Transactional(readOnly = true)
	public BonusDTO getBonusById(Long bonusId) {
		Bonus bonus = bonusRepository.getReferenceById(bonusId);
		log.debug("Get bonus with id: " + bonusId + " returned: " + bonus.toString());
		return modelMapper.map(bonus, BonusDTO.class);
	}

	/**
	 * Saves a bonus.
	 *
	 * @param bonusDTO the bonus to be saved
	 * @return the saved bonus
	 */
	public BonusDTO saveBonus(BonusDTO bonusDTO) {
		bonusRepository.save(modelMapper.map(bonusDTO, Bonus.class));
		log.debug("Saved bonus: " + bonusDTO.toString());
		return bonusDTO;
	}

	/**
	 * Updates a bonus.
	 *
	 * @param bonusDTO the bonus to be updated
	 * @return the updated bonus
	 */
	@Transactional(readOnly = true)
	public BonusDTO updateBonus(BonusDTO bonusDTO) {
		bonusRepository.save(modelMapper.map(bonusDTO, Bonus.class));
		log.debug("Updated bonus: " + bonusDTO.toString());
		return bonusDTO;
	}

	private BonusDTO companyBonusCreator(EmployeeDTO employee, String season) {
		BonusDTO newBonus = new BonusDTO();
		BigDecimal rate = BonusBySeason.resolveOfEnum(season).getRate();
		newBonus.setAmount(rate.multiply(employee.getSalary()));
		newBonus.setEmployee(employee);
		newBonus.setCompany(employee.getCompany());
		return newBonus;
	}
}
