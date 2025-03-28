package com.swift.project.controllers;

import com.swift.project.DTOs.BanksByCountryDTO;
import com.swift.project.DTOs.HqDTO;
import com.swift.project.DTOs.Message;
import com.swift.project.data.BankEntity;
import com.swift.project.service.BankService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        BanksByCountryDTO banks = bankService.getBanksByCountryDTO(countryISO2code);
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @GetMapping("/{swift}")
    public ResponseEntity<Object> getBank(@PathVariable String swift) {
        Optional<BankEntity> bank = bankService.getBank(swift);

        if(bank.isPresent() && !bank.get().getIsHeadquater()) return new ResponseEntity<>(bank.get(), HttpStatus.OK);
        HqDTO hqDTO = bankService.getHqDTO(swift);
        return new ResponseEntity<>(hqDTO, HttpStatus.OK);
        }
    @DeleteMapping("/{swift}")
    public ResponseEntity<Object> delBank(@PathVariable String swift){
        bankService.removeBank(swift);
        Message mes = new Message("Bank with Swift Code: " + swift + "has been successfully deleted");
        return new ResponseEntity<>(mes, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Object> addBank(@Valid @RequestBody BankEntity bank){
        bankService.saveBank(bank);
        Message mes = new Message("Bank with Swift Code: " + bank.getSwiftCode() + "has been successfully added");
        return new ResponseEntity<>(mes, HttpStatus.OK);
    }
}





