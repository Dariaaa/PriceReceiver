package com.example.pricereceiver.processing;

import com.example.pricereceiver.entity.Company;
import com.example.pricereceiver.entity.CustomTitle;
import com.example.pricereceiver.entity.PriceEntity;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Slf4j
@AllArgsConstructor
public class CsvProcessor  {


    public Set<PriceEntity> process(InputStream stream, Company company) throws IOException {
        log.info("start processing csv file");
        Reader reader = new InputStreamReader(stream);

        CSVParser csvParser = new CSVParserBuilder().withSeparator(';')
                .withQuoteChar('\"')
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(csvParser).build();

        List<String> titles = Arrays.asList(csvReader.readNext());

        Set<PriceEntity> result = new HashSet<>();

        List<CustomTitle> customTitles = checkFile(titles,company);

        if (titles.size() == 0) {
            return null;
        }
        String [] line = null;
        while (true){
            try {

                line = csvReader.readNext();
            }catch (IOException e){
                log.info("Uncorrect part of file");
                break;
            }

            if (line==null||line.length == 0) {
                break;
            }
            try {
                PriceEntity entity = new PriceEntity();

                for(CustomTitle customTitle : customTitles){
                    if (customTitle.getCsvIndex()==-1){
                        continue;
                    }
                    String value = line[customTitle.getCsvIndex()];
                    if (value == null || value.isEmpty()) {
                        continue;
                    }
                    String fieldName = customTitle.getEntityTitle();
                    entity.setValue(fieldName,value);
                }
                if (result.size() % 1000 == 0) {
                    log.info("Processing: " + result.size() + " line");
                }
                result.add(entity);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Processing: " + result.size() + " line");
            }
        }
        reader.close();
        csvReader.close();
        log.info("finish processing csv file");
        return result;
    }

    private List<CustomTitle> checkFile(List<String> titles, Company company){
        List<CustomTitle> customTitles = company.getCustomTitles();
        if (customTitles!=null && !customTitles.isEmpty()){
            titles.forEach(title->{
                String splittedTitle = title;
                if (splittedTitle.contains(",")) {
                    splittedTitle = title.split(",")[0];
                }
                CustomTitle customTitle = new CustomTitle(splittedTitle);
                if (customTitles.contains(customTitle)) {
                    customTitle = customTitles.get(customTitles.indexOf(customTitle));
                    customTitle.setCsvIndex(titles.indexOf(title));
                }else {
                    log.info(String.format("Title \"%s\" was not found in settings for %s",title,company));
                    customTitle.setCsvIndex(-1);
                }
            });
        }
        return customTitles;
    }



}
