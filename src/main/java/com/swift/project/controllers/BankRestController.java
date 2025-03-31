package com.swift.project.controllers;

import com.swift.project.DTOs.BanksByCountryDTO;
import com.swift.project.DTOs.HqDTO;
import com.swift.project.DTOs.Message;
import com.swift.project.DTOs.SingleBankDTO;
import com.swift.project.other.Messages;
import com.swift.project.service.BankService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/v1/swift-codes")
public class BankRestController {
    private static final Logger logger = LoggerFactory.getLogger(BankRestController.class);

    private final BankService bankService;

    public BankRestController(BankService bankService) {
        logger.info("BANK REST CONTROL START");
        this.bankService = bankService;
    }

    @GetMapping("/country/{countryISO2code}")
    public ResponseEntity<Object> allBanksByCountryCode(@PathVariable String countryISO2code) {
        BanksByCountryDTO banks = bankService.getBanksByCountryDTO(countryISO2code);
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @GetMapping("/{swift}")
    public ResponseEntity<Object> getBank(@PathVariable String swift) {
        SingleBankDTO singleBankDTO = bankService.getBank(swift);
        if (!singleBankDTO.isHeadquarter()) return new ResponseEntity<>(singleBankDTO, HttpStatus.OK);
        HqDTO hqDTO = bankService.getHqDTO(swift);
        return new ResponseEntity<>(hqDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{swift}")
    public ResponseEntity<Object> delBank(@PathVariable String swift) {
        bankService.removeBank(swift);
        Message mes = new Message(Messages.bankSuccessfullyDeleted(swift));
        return new ResponseEntity<>(mes, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Object> addBank(@Valid @RequestBody SingleBankDTO singleBankDTO) {
        bankService.addBank(singleBankDTO);
        Message mes = new Message(Messages.bankSuccessfullyAdded(singleBankDTO.getSwiftCode()));
        return new ResponseEntity<>(mes, HttpStatus.OK);
    }
}





