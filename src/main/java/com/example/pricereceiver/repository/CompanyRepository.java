package com.example.pricereceiver.repository;

import com.example.pricereceiver.entity.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company,Long> {
    Company findByEmail(String email);
}
