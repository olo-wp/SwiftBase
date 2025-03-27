package com.swift.project.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
@Table(name = "banks")
public class BankEntity {
    @Column(name = "address")
    private String address;
    @Column(name = "bankName")
    private String bankName;
    @Column(name = "countryISO2")
    private String countryISO2;
    @Column(name = "countryName")
    private String countryName;
    @Column(name = "isHeadquater")
    private Boolean isHeadquater;
    @Id
    @Column(name = "swiftCode")
    private String swiftCode;
    public BankEntity() {

    }
}
