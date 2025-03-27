package com.swift.project.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BanksByCountryDTO {
    private String countryISO2;
    private String countryName;
    List<BranchDTO> countryBanks;
}
