package com.example.pricereceiver.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Company {
    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column
    private String email;
    @OneToMany(fetch = FetchType.EAGER)
    private List<CustomTitle> customTitles;
}

