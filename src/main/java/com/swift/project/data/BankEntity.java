package com.swift.project.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "banks")
@NoArgsConstructor
@AllArgsConstructor
public class BankEntity {
    @Column(name = "address")
    private String address;
    @Column(name = "bank_Name")
    private String bankName;
    @Column(name = "countryISO2")
    private String countryISO2;
    @Column(name = "country_Name")
    private String countryName;
    @Column(name = "is_Headquarter")
    private Boolean isHeadquarter;
    @Id
    @Column(name = "swift_Code")
    private String swiftCode;
}
