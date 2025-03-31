package com.swift.project.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonPropertyOrder({"address", "bankName", "countryISO2", "isHeadquarter", "swiftCode"})
public class BranchDTO {
    private String address;
    private String bankName;
    private String countryISO2;
    @JsonProperty("isHeadquarter")
    private Boolean isHeadquarter;
    private String swiftCode;
}
