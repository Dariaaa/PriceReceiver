package com.example.pricereceiver.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(exclude = {"id","entityTitle","csvIndex"})
public class CustomTitle {
    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column
    private String csvTitle;
    @Column
    private String entityTitle;
    @Transient
    private Integer csvIndex;

    public CustomTitle(String csvTitle){
        this.csvTitle = csvTitle;
    }
    public CustomTitle(String csvTitle, String entityTitle){
        this.csvTitle = csvTitle;
        this.entityTitle = entityTitle;
    }
}
