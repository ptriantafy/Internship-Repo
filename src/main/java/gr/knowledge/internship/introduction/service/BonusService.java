package gr.knowledge.internship.introduction.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.knowledge.internship.introduction.dto.BonusDTO;
import gr.knowledge.internship.introduction.dto.CompanyDTO;
import gr.knowledge.internship.introduction.dto.EmployeeDTO;
import gr.knowledge.internship.introduction.entity.Bonus;
import gr.knowledge.internship.introduction.entity.BonusBySeason;
import gr.knowledge.internship.introduction.exception.SeasonNotFoundException;
import gr.knowledge.internship.introduction.filtering.CalculateBonusFilter;
import gr.knowledge.internship.introduction.filtering.CompanyBonusFilter;
import gr.knowledge.internship.introduction.repository.BonusRepository;

@Service
@Transactional
public class BonusService {

	@Autowired
	private BonusRepository bonusRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ModelMapper modelMapper;

	public BonusDTO saveBonus(BonusDTO bonusDTO) {
		bonusRepository.save(modelMapper.map(bonusDTO, Bonus.class));
		return bonusDTO;
	}
	
	@Transactional(readOnly = true)
	public BigDecimal calculateBonus(CalculateBonusFilter parameter) {
		if (parameter.getSalary().compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Salary cannot be negative.");
		}
		try {
			return BonusBySeason.valueOf(parameter.getSeason().toUpperCase()).getRate().multiply(parameter.getSalary());
		} catch (IllegalArgumentException ex) {
			throw new SeasonNotFoundException(parameter.getSeason());
		}
	}

	@Transactional(readOnly = true)
	public List<BonusDTO> getAllBonus() {
		List<Bonus> bonusList = bonusRepository.findAll();
		return modelMapper.map(bonusList, new TypeToken<List<BonusDTO>>() {
		}.getType());
	}

	@Transactional(readOnly = true)
	public BonusDTO getBonusById(int bonusId) {
		Bonus bonus = bonusRepository.getReferenceById(bonusId);
		return modelMapper.map(bonus, BonusDTO.class);
	}

	@Transactional(readOnly = true)
	public BonusDTO updateBonus(BonusDTO bonusDTO) {
		bonusRepository.save(modelMapper.map(bonusDTO, Bonus.class));
		return bonusDTO;
	}

	public boolean deleteBonus(BonusDTO bonusDTO) {
		bonusRepository.delete(modelMapper.map(bonusDTO, Bonus.class));
		return true;
	}

	public List<BonusDTO> createCompanyBonus(CompanyBonusFilter companyParameter) {
		BonusBySeason seasonEnum;
		try {
			seasonEnum = BonusBySeason.valueOf(companyParameter.getSeason().toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new SeasonNotFoundException(companyParameter.getSeason());
		}
		List<EmployeeDTO> employeeList = employeeService.getCompanyEmployees(companyParameter.getCompanyId());
//		stream employees and create bonus for each
		List<BonusDTO> bonusList = employeeList.stream().map(e -> new BonusDTO(null, e,
				modelMapper.map(e.getCompany(), CompanyDTO.class), seasonEnum.getRate().multiply(e.getSalary())))
				.collect(Collectors.toList());
		bonusRepository.saveAll(modelMapper.map(bonusList, new TypeToken<List<Bonus>>() {
		}.getType()));

		return bonusList;
	}
}
