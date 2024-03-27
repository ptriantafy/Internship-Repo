package gr.knowledge.internship.introduction.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.knowledge.internship.introduction.dto.BonusDTO;
import gr.knowledge.internship.introduction.filtering.CalculateBonusFilter;
import gr.knowledge.internship.introduction.filtering.CompanyBonusFilter;
import gr.knowledge.internship.introduction.service.BonusService;

@RestController
@RequestMapping(value="/bonus")
public class BonusController {

    @Autowired
    private BonusService bonusService;

    @GetMapping("/bonus-calculation")
    public BigDecimal calculateBonus(CalculateBonusFilter calculateBonusParameter) {
    	return bonusService.calculateBonus(calculateBonusParameter);
    }

    @PostMapping("/company-bonus")
    public List<BonusDTO> createCompanyBonus(CompanyBonusFilter companyBonusParameter){
    	return bonusService.createCompanyBonus(companyBonusParameter);
    }

    @DeleteMapping("/bonus-deletion")
    public void deleteBonus(@RequestBody BonusDTO bonusDTO){
        bonusService.deleteBonus(bonusDTO);
    }

    @GetMapping
    public List<BonusDTO> getBonus(){
        return bonusService.getAllBonus();
    }
    @GetMapping("/{bonusId}")
    public BonusDTO getBonusById(@PathVariable Long bonusId) {
    	return bonusService.getBonusById(bonusId);
    }

    @PostMapping("/bonus-save")
    public BonusDTO saveBonus(@RequestBody BonusDTO bonusDTO){
        return bonusService.saveBonus(bonusDTO);
    }

    @PutMapping("/bonus-update")
    public BonusDTO updateBonus(@RequestBody BonusDTO bonusDTO){
        return bonusService.updateBonus(bonusDTO);
    }

}
