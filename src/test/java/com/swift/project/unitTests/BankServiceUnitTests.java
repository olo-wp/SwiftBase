package com.swift.project.unitTests;

import com.swift.project.DTOs.BanksByCountryDTO;
import com.swift.project.DTOs.HqDTO;
import com.swift.project.DTOs.SingleBankDTO;
import com.swift.project.exceptions.BankAlreadyInDataBaseException;
import com.swift.project.exceptions.BankNotFoundException;
import com.swift.project.exceptions.IllegalISO2CodeException;
import com.swift.project.exceptions.IllegalSwiftCodeException;
import com.swift.project.service.BankService;
import com.swift.project.service.XlsxParseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
class BankServiceUnitTests {

    /* we mock this field, so that BankServiceTests are made only on
    the H2 database.
     */
    @MockitoBean
    private XlsxParseService xlsxParseService;

    @Autowired
    private BankService bankService;

    @Test
    void testBankServiceGetByIdReturns() {
        SingleBankDTO singleBankDTO = bankService.getBank("AAISALTRXXX");
        assertNotNull(singleBankDTO);
        assert (singleBankDTO.getBankName().strip().equals("UNITED BANK OF ALBANIA SH.A"));

        singleBankDTO = bankService.getBank("CBARAWAWXXX");
        assertNotNull(singleBankDTO);
        assert (singleBankDTO.getBankName().strip().equals("CENTRALE BANK VAN ARUBA"));
    }

    @Test
    void testBankServiceGetByIdThrows() {
        assertThrows(BankNotFoundException.class, () -> bankService.getBank("abcdehijk"));
        assertThrows(IllegalSwiftCodeException.class, () -> bankService.getBank("axxxxxxxxxxxxxifshfhjfshfahfx"));
    }

    @Test
    void testAddAndRemove() {
        SingleBankDTO singleBankDTO = new SingleBankDTO(
                " ",
                "Rich People Bank",
                "LI",
                "LIECHTENSTEIN",
                true,
                "12345678XXX"
        );


        long size = bankService.getBankRepositorySize();

        bankService.addBank(singleBankDTO);

        assert (size + 1 == bankService.getBankRepositorySize());

        bankService.removeBank("12345678XXX");

        assert (size == bankService.getBankRepositorySize());
    }

    @Test
    void testRemoveNonExistentThrows() {
        assertThrows(BankNotFoundException.class, () -> bankService.removeBank("12345678XXX"));
        assertThrows(IllegalSwiftCodeException.class, () -> bankService.removeBank("123X"));
        assertThrows(IllegalSwiftCodeException.class, () -> bankService.removeBank(null));
    }

    @Test
    void testAddAlreadyInDataBaseThrows() {
        SingleBankDTO singleBankDTO = new SingleBankDTO(
                " ",
                " ",
                "PL",
                "POLAND",
                false,
                "BREXPLPWWAL"
        );
        assertThrows(BankAlreadyInDataBaseException.class, () -> bankService.addBank(singleBankDTO));
        singleBankDTO.setSwiftCode("abc");
        assertThrows(IllegalSwiftCodeException.class, () -> bankService.addBank(singleBankDTO));
        singleBankDTO.setSwiftCode("BREXPLPWWXX");
        singleBankDTO.setCountryISO2("-");
        assertThrows(IllegalISO2CodeException.class, () -> bankService.addBank(singleBankDTO));
    }

    @Test
    void testFindByCountryThrowsByWrongISO2() {
        String countryCode = "WB";
        assertThrows(BankNotFoundException.class, () -> bankService.getBanksByCountryDTO(countryCode));
        assertThrows(IllegalISO2CodeException.class, () -> bankService.getBanksByCountryDTO(null));
    }

    @Test
    void testFindByCountryReturns() {
        String countryCode = "PL";
        BanksByCountryDTO dto = bankService.getBanksByCountryDTO(countryCode);
        assert (dto.getCountryBanks().size() == 5);
        String countryCode2 = "AL";
        dto = bankService.getBanksByCountryDTO(countryCode2);
        assert (dto.getCountryBanks().size() == 2);
    }

    @Test
    void testGetHqDTOReturn() {
        String swiftCode = "BREXPLPWXXX";
        HqDTO HqDTO = bankService.getHqDTO(swiftCode);
        assertTrue(HqDTO.getIsHeadquarter());
        assert (HqDTO.getBranches().size() == 3);
    }

    @Test
    void testGetHqDTOThrowsByWrongSwiftCode() {
        String swiftCode = "jfa321222k";
        assertThrows(BankNotFoundException.class, () -> bankService.getHqDTO(swiftCode));
        String swiftCode2 = "abc";
        assertThrows(IllegalSwiftCodeException.class, () -> bankService.getHqDTO(swiftCode2));
        assertThrows(IllegalSwiftCodeException.class, () -> bankService.getHqDTO(null));
    }

}

