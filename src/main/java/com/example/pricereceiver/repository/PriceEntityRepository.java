package com.example.pricereceiver.repository;

import com.example.pricereceiver.entity.PriceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceEntityRepository extends CrudRepository<PriceEntity,Long> {


}
