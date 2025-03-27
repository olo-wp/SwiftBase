package com.swift.project;

import com.swift.project.data.BankEntity;
import com.swift.project.service.BankService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Transactional
class BankServiceTests {

    @Autowired
    BankServiceTests(BankService bankService){
        this.bankService = bankService;
    }
    public BankService bankService;

    @Test
    void testBankServiceGetById(){
        Optional<BankEntity> bankEntity = bankService.getBank("AAISALTRXXX");
        assert (bankEntity.isPresent());
        assert (bankEntity.get().getBankName().equals("UNITED BANK OF ALBANIA SH.A"));

        Optional<BankEntity> bankEntity2 = bankService.getBank("BSCHCLR10R7");
        assert (bankEntity2.isPresent());
        assert (bankEntity2.get().getBankName().equals("BANCO SANTANDER CHILE"));
        assert (bankEntity2.get().getAddress().isBlank());
        Optional<BankEntity> bankEntityNonExistent = bankService.getBank("abcdefghijk");
        assert bankEntityNonExistent.isEmpty();

    }

    @Test
    void testAddAndRemove(){
        BankEntity bankEntity = new BankEntity();
        bankEntity.setIsHeadquater(true);
        bankEntity.setCountryName("Liechtenstein");
        bankEntity.setCountryISO2("LI");
        bankEntity.setBankName("Rich People Bank");
        bankEntity.setSwiftCode("12345678XXX");

        int size = bankService.getBankRepoSize();

        bankService.saveBank(bankEntity);

        assert (size + 1 == bankService.getBankRepoSize());

        bankService.removeBank("12345678XXX");

        assert (size == bankService.getBankRepoSize());
    }

    @Test
    void testRemoveNonExistent(){
        int size = bankService.getBankRepoSize();
        bankService.removeBank("12345678XXX");
        assert (size == bankService.getBankRepoSize());
    }
}
