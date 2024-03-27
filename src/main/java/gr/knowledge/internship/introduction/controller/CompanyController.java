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

import gr.knowledge.internship.introduction.dto.CompanyDTO;
import gr.knowledge.internship.introduction.service.CompanyService;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @DeleteMapping("/company-deletion")
    public boolean deleteCompany(@RequestBody CompanyDTO companyDTO){
        return companyService.deleteCompany(companyDTO);
    }

    @GetMapping
    public List<CompanyDTO> getCompany(){
        return companyService.getAllCompanies();
    }

    @GetMapping("/{companyId}")
    public CompanyDTO getCompanyById(@PathVariable Long companyId){
        return companyService.getCompanyById(companyId);
    }

    @GetMapping("/{companyId}/expenses")
    public BigDecimal getMonthlyExpenses(@PathVariable int companyId) {
    	return companyService.getMonthlyExpenses(companyId);
    }

    @PostMapping("/company-save")
    public CompanyDTO saveCompany(@RequestBody CompanyDTO companyDTO){
        return companyService.saveCompany(companyDTO);
    }

    @PutMapping("/company-update")
    public CompanyDTO updateCompany(@RequestBody CompanyDTO companyDTO){
        return companyService.updateCompany(companyDTO);
    }
}
