package com.swift.project.service;

import com.swift.project.data.BankEntity;
import com.swift.project.repo.BankRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    private final BankRepo bankRepo;
    @Autowired
    BankService(BankRepo bankRepo){
        this.bankRepo = bankRepo;
    }
    @Transactional
    public void saveBank(BankEntity bank){
        bankRepo.save(bank);
    }

    @Transactional
    public void removeBank(String swiftCode){
        bankRepo.deleteById(swiftCode);
    }

    @Transactional
    public void saveBanks(List<BankEntity> banks){
        bankRepo.saveAll(banks);
    }

    public Optional<BankEntity> getBank(String swift){
        return bankRepo.findById(swift);
    }

    public Integer getBankRepoSize(){
        return bankRepo.findAll().size();
    }

    public List<BankEntity> getBanksByISO2(String iso2){
        return bankRepo.findAllByISO2(iso2);
    }

}
