package com.example.pricereceiver.service;

import com.example.pricereceiver.entity.PriceEntity;
import com.example.pricereceiver.repository.PriceEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@AllArgsConstructor
public class PriceEntityService {

    private final PriceEntityRepository priceEntityRepository;

    @Transactional
    public void saveAll(Collection<PriceEntity> priceEntities){
        priceEntityRepository.saveAll(priceEntities);
    }
}
