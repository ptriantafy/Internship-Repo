package gr.knowledge.internship.introduction.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.knowledge.internship.introduction.dto.BonusDTO;
import gr.knowledge.internship.introduction.parameterobject.CalculateBonusParameter;
import gr.knowledge.internship.introduction.parameterobject.CompanyBonusParameter;
import gr.knowledge.internship.introduction.service.BonusService;

@RestController
@RequestMapping(value="/bonus")
@CrossOrigin
public class BonusController {

    @Autowired
    private BonusService bonusService;

    @GetMapping("/")
    public List<BonusDTO> getBonus(){
        return bonusService.getAllBonus();
    }
    
    @GetMapping("/calculate-bonus")
    public BigDecimal calculateBonus(CalculateBonusParameter calculateBonusParameter) {
    	return bonusService.calculateBonus(calculateBonusParameter);
    }
    
    @GetMapping("/{bonusId}")
    public BonusDTO getBonusById(@PathVariable int bonusId) {
    	return bonusService.getBonusById(bonusId);
    }

    @PostMapping("/company-bonus")
    public List<BonusDTO> createCompanyBonus(CompanyBonusParameter companyBonusParameter){
    	return bonusService.createCompanyBonus(companyBonusParameter);
    }
    @PostMapping("/save-bonus")
    public BonusDTO saveBonus(@RequestBody BonusDTO bonusDTO){
        return bonusService.saveBonus(bonusDTO);
    }

    @PutMapping("/update-bonus")
    public BonusDTO updateBonus(@RequestBody BonusDTO bonusDTO){
        return bonusService.updateBonus(bonusDTO);
    }

    @DeleteMapping("/delete-bonus")
    public boolean deleteBonus(@RequestBody BonusDTO bonusDTO){
        return bonusService.deleteBonus(bonusDTO);
    }
    
}