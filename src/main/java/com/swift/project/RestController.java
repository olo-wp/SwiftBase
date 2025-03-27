package com.swift.project;

import com.swift.project.data.BankEntity;
import com.swift.project.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final BankService bankService;
    @Autowired
    public RestController(BankService bankService){
        this.bankService = bankService;
    }
    @GetMapping("/v1/swift-codes/country/{countryISO2code}")
    List<BankEntity> allBanksByCountryCode(@PathVariable String countryISO2code){
        List<BankEntity> le = bankService.getBanksByISO2(countryISO2code);
        System.out.println(le);
        return le;
    }

    @GetMapping("/{swift}")
    public ResponseEntity<BankEntity> getBank(@PathVariable String swift) {
        Optional<BankEntity> bank = bankService.getBank(swift);
        if (bank.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bank.get(), HttpStatus.OK);
    }
}
