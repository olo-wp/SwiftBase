package com.swift.project.controllers;

import com.swift.project.DTOs.BanksByCountryDTO;
import com.swift.project.data.BankEntity;
import com.swift.project.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/v1/swift-codes")
public class RestController {

    private final BankService bankService;
    public RestController(BankService bankService){
        System.out.println("REST CONTROL START");
        this.bankService = bankService;
    }
    @GetMapping("/country/{countryISO2code}")
    public ResponseEntity<Object> allBanksByCountryCode(@PathVariable String countryISO2code){
        try{
            BanksByCountryDTO banks = bankService.getBanksByCountryDTO(countryISO2code);
            return new ResponseEntity<>(banks, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{swift}")
    public ResponseEntity<Object> getBank(@PathVariable String swift) {
        Optional<BankEntity> bank = bankService.getBank(swift);
        if (bank.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!bank.get().getIsHeadquater()) return new ResponseEntity<>(bank.get(), HttpStatus.OK);
        return new ResponseEntity<>(bankService.getHqDTO(swift), HttpStatus.OK);
    }

}
