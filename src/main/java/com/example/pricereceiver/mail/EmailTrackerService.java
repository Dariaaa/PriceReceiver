package com.example.pricereceiver.mail;

import com.example.pricereceiver.entity.Company;
import com.example.pricereceiver.entity.PriceEntity;
import com.example.pricereceiver.processing.CsvProcessor;
import com.example.pricereceiver.service.CompanyService;
import com.example.pricereceiver.service.PriceEntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class EmailTrackerService implements Processor {

    private final PriceEntityService priceEntityService;
    private final CompanyService companyService;


    public EmailTrackerService(
                               PriceEntityService priceEntityService,
                               CompanyService companyService){
        this.priceEntityService = priceEntityService;
        this.companyService = companyService;
    }


    @Override
    public void process(Exchange exchange) {

        Message messageCopy = exchange.getIn().copy();

        String senderEmail = messageCopy.getHeader("Return-Path", String.class);

        Company company = companyService.findByEmail(senderEmail);
        if (company==null || company.getCustomTitles()==null || company.getCustomTitles().isEmpty()){
            return;
        }

        Map<String, DataHandler> attachments = messageCopy.getAttachments();
        if (attachments.size() > 0) {

            for(String filename:attachments.keySet()){

                DataHandler dHandler = attachments.get(filename);
                if (dHandler.getContentType() == null ||
                        !dHandler.getContentType().contains("text/csv")) {
                    log.info("Unsupported type");
                    continue;
                }
                try {
                    Set<PriceEntity> priceEntityList =
                              new CsvProcessor().process(dHandler.getInputStream(),
                                    company);

                    priceEntityService.saveAll(priceEntityList);

                } catch (IOException e) {
                    log.error("Error reading file",e);
                    return;
                }

                log.info("Processed attachment, file name : " + filename);

            }
        }
    }

}
