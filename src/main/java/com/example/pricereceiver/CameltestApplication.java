package com.example.pricereceiver;

import com.example.pricereceiver.entity.Company;
import com.example.pricereceiver.entity.CustomTitle;
import com.example.pricereceiver.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class CameltestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CameltestApplication.class, args);
    }

    @Autowired
    private CompanyService companyService;

    @Value("${camel.imap.sender.email}")
    private String companyEmail;

    @Override
    public void run(String... args) {
        Company company = new Company();
        company.setEmail(companyEmail);
        List<CustomTitle> customTitleList = new ArrayList<>();
        customTitleList.add(new CustomTitle("Бренд","Vendor"));
        customTitleList.add(new CustomTitle("Каталожный номер","Number"));
        customTitleList.add(new CustomTitle("Описание","Description"));
        customTitleList.add(new CustomTitle("Цена","Price"));
        customTitleList.add(new CustomTitle("Наличие","Count"));
        company.setCustomTitles(customTitleList);
        companyService.save(company);
    }
}
