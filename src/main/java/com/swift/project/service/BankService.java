package com.swift.project.service;

import com.swift.project.DTOs.BanksByCountryDTO;
import com.swift.project.DTOs.BranchDTO;
import com.swift.project.DTOs.HqDTO;
import com.swift.project.data.BankEntity;
import com.swift.project.repo.BankRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankService {

    private final BankRepo bankRepo;
    @Autowired
    BankService(BankRepo bankRepo){
        this.bankRepo = bankRepo;
    }

    public HqDTO getHqDTO(String swift) throws RuntimeException{

        List<BankEntity> banks = bankRepo.findBySwiftCodeStartingWith(swift.substring(0,8));

        BankEntity hq = banks.stream().filter(BankEntity::getIsHeadquater).findFirst()
                .orElseThrow(() -> new RuntimeException("hq not found"));

        List<BranchDTO> branches = banks.stream().map(bank -> new BranchDTO(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountryISO2(),
                bank.getIsHeadquater(),
                bank.getSwiftCode()
        )).collect(Collectors.toList());

        return new HqDTO(
                hq.getAddress(),
                hq.getBankName(),
                hq.getCountryISO2(),
                hq.getCountryName(),
                hq.getIsHeadquater(),
                hq.getSwiftCode(),
                branches
        );
    }
    @Transactional
    public void saveBank(BankEntity bank){
        bankRepo.save(bank);
    }

    @Transactional
    public void removeBank(String swiftCode){
        bankRepo.deleteById(swiftCode);
    }
    public Optional<BankEntity> getBank(String swift){
        return bankRepo.findById(swift);
    }

    public Integer getBankRepoSize(){
        return bankRepo.findAll().size();
    }

    public BanksByCountryDTO getBanksByCountryDTO(String iso2) throws RuntimeException{
        List<BankEntity> banks = bankRepo.findAllBycountryISO2(iso2);
        if (banks.isEmpty()) throw new RuntimeException("no bank with ISO2: " + iso2 + "found");

        String countryISO2 = banks.get(0).getCountryISO2();
        String countryName = banks.get(0).getCountryName();

        List<BranchDTO> banksInCountry = banks.stream().map(bank -> new BranchDTO(
                    bank.getAddress(),
                    bank.getBankName(),
                    bank.getCountryISO2(),
                    bank.getIsHeadquater(),
                    bank.getSwiftCode()
                )).collect(Collectors.toList());
        return new BanksByCountryDTO(
                countryISO2,
                countryName,
                banksInCountry
        );
    }

}
