package com.swift.project;

import com.swift.project.DTOs.BanksByCountryDTO;
import com.swift.project.data.BankEntity;
import com.swift.project.exceptions.BankNotFoundException;
import com.swift.project.repo.BankRepo;
import com.swift.project.service.BankNotFoundAdvice;
import com.swift.project.service.BankService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
class BankServiceTests {


    @Autowired
    private BankService bankService;


    @Test
    void testBankServiceGetByIdReturns(){
        Optional<BankEntity> bankEntity = bankService.getBank("AAISALTRXXX");
        assert (bankEntity.isPresent());
        assert (bankEntity.get().getBankName().strip().equals("UNITED BANK OF ALBANIA SH.A"));

        Optional<BankEntity> bankEntity2 = bankService.getBank("CBARAWAWXXX");
        assert (bankEntity2.isPresent());
        assert (bankEntity2.get().getBankName().strip().equals("CENTRALE BANK VAN ARUBA"));
        assert (bankEntity2.get().getAddress().isBlank());
    }

    @Test
    void testBankServiceGetByIdThrows(){
        assertThrows(BankNotFoundException.class, () -> {
            bankService.getBank("abcdefghijk");
        });
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
    void testRemoveNonExistentThrows(){
        assertThrows(BankNotFoundException.class, () -> {
            bankService.removeBank("12345678XXX");
        });
    }

    @Test
    void testFindByCountryThrowsByWrong(){
        String countryCode = "WB";
        assertThrows(BankNotFoundException.class, () -> {
            bankService.getBanksByCountryDTO(countryCode);
        });
        assertThrows(BankNotFoundException.class, () -> {
            bankService.getBanksByCountryDTO(null);
        });
    }

    @Test
    void testFindByCountryReturns(){
        String countryCode = "PL";
        BanksByCountryDTO dto = bankService.getBanksByCountryDTO(countryCode);
        assert (dto.getCountryBanks().size()==459); //459 is the number of polish banks in the xlsx file
        countryCode = "LV";
        dto = bankService.getBanksByCountryDTO(countryCode);
        assert (dto.getCountryBanks().size()==71); //analogical to the first one
        countryCode = "BG";
        dto = bankService.getBanksByCountryDTO(countryCode);
        assert (dto.getCountryBanks().size()==133);
    }
}
