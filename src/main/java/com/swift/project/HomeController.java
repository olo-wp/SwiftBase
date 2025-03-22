package com.swift.project;

import com.swift.project.data.BankEntity;
import com.swift.project.repo.BankRepo;
import com.swift.project.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class HomeController {

    private final BankService bankService;

    @Autowired
    public HomeController(BankService bankService) {
        this.bankService = bankService;
    }

    @RequestMapping("/")
    public String index(){
        return "index.html";
    }

    @GetMapping("/v1/swift-codes/country/{countryISO2code}")
    List<BankEntity> allBanksByCountryCode(@PathVariable String countryISO2code){
        List<BankEntity> le = bankService.getBanksByISO2(countryISO2code);
        System.out.println(le);
        return le;
    }

    @GetMapping("/{swift}")
    public ResponseEntity<BankEntity> getBank(@PathVariable String swift) {
        BankEntity bank = bankService.getBank(swift);
        if (bank == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }

}
