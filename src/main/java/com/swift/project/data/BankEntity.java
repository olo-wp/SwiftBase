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
    @Id
    @Column(name = "swift_code")
    private String swift_code;
    @Column(name = "ISO2")
    private String ISO2;
    @Column(name = "bank_name")
    private String bank_name;
    @Column(name = "address")
    private String address;
    @Column(name = "country")
    private String country;
    @Column(name = "is_hq")
    private Boolean is_hq;
    public BankEntity() {

    }
}
