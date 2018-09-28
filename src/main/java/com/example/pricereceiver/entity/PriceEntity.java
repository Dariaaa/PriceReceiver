package com.example.pricereceiver.entity;

import com.example.pricereceiver.util.PriceEntityValidator;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class PriceEntity {
    @Id
    @Column(length = 64)
    private String Number;
    @Column(length = 64)
    private String Vendor;
    @Column(length = 64)
    private String SearchVendor;
    @Column(length = 64)
    private String SearchNumber;
    @Column(length = 512)
    private String Description;
    @Column(precision=18, scale=2)
    private Double Price;
    @Column
    private Integer Count;

    public void setValue(String fieldName,String value){
        if (value==null|| value.isEmpty()){
            return;
        }
        if (fieldName.contains("Vendor")){
            this.setVendor(value);
            value = value.replace("^[^\\w]$","").toUpperCase();
            this.setSearchVendor(value);

        }else if (fieldName.contains("Number")){
            this.setNumber(value);
            value = value.replace("^[^\\w]$","").toUpperCase();
            this.setSearchNumber(value);

        }else if (fieldName.contains("Count")){
            if (PriceEntityValidator.match(value)){
                value = value.split("[<>-]")[1];
            }
            setCount(Integer.valueOf(value));

        }else if (fieldName.contains("Description")){
            value = value.length()>512 ? value.substring(512): value;
            this.setDescription(value);

        }else if (fieldName.contains("Price")){
            value = value.replace(",",".");
            this.setPrice(Double.valueOf(value));
        }
    }
}
