package com.example.pricereceiver.service;

import com.example.pricereceiver.entity.Company;
import com.example.pricereceiver.repository.CompanyRepository;
import com.example.pricereceiver.repository.CustomTitleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CustomTitleRepository customTitleRepository;

    public Company findByEmail(String email){
        return companyRepository.findByEmail(email);
    }

    public void save(Company company){
        customTitleRepository.saveAll(company.getCustomTitles());
        companyRepository.save(company);
    }

}
