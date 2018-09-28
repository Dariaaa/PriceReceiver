package com.example.pricereceiver.repository;

import com.example.pricereceiver.entity.CustomTitle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomTitleRepository extends CrudRepository<CustomTitle,Long> {

}
