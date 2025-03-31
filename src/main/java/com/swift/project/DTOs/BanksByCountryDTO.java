package com.swift.project.DTOs;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"countryISO2", "countryName", "countryBanks"})
public class BanksByCountryDTO {
    private String countryISO2;
    private String countryName;
    private List<BranchDTO> countryBanks;
}
