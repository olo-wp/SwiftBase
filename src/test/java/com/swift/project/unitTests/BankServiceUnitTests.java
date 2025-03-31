package com.swift.project.unitTests;

import com.swift.project.DTOs.BanksByCountryDTO;
import com.swift.project.DTOs.HqDTO;
import com.swift.project.DTOs.SingleBankDTO;
import com.swift.project.exceptions.BankAlreadyInDataBaseException;
import com.swift.project.exceptions.BankNotFoundException;
import com.swift.project.exceptions.WrongSwiftCodeException;
import com.swift.project.service.BankService;
import com.swift.project.service.XlsxParseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        assertThrows(WrongSwiftCodeException.class, () -> bankService.getBank("axxxxxxxxxxxxxifshfhjfshfahfx"));
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

        bankService.saveBank(singleBankDTO);

        assert (size + 1 == bankService.getBankRepositorySize());

        bankService.removeBank("12345678XXX");

        assert (size == bankService.getBankRepositorySize());
    }

    @Test
    void testRemoveNonExistentThrows() {
        assertThrows(BankNotFoundException.class, () -> bankService.removeBank("12345678XXX"));
        assertThrows(WrongSwiftCodeException.class, () -> bankService.removeBank("123X"));
        assertThrows(WrongSwiftCodeException.class, () -> bankService.removeBank(null));
    }

    @Test
    void testAddAlreadyInDataBaseThrows() {
        SingleBankDTO singleBankDTO = new SingleBankDTO(
                " ",
                " ",
                " ",
                " ",
                true,
                "BREXPLPWWAL"
        );
        assertThrows(BankAlreadyInDataBaseException.class, () -> bankService.saveBank(singleBankDTO));
        singleBankDTO.setSwiftCode("abc");
        assertThrows(WrongSwiftCodeException.class, () -> bankService.saveBank(singleBankDTO));
    }

    @Test
    void testFindByCountryThrowsByWrongISO2() {
        String countryCode = "WB";
        assertThrows(BankNotFoundException.class, () -> bankService.getBanksByCountryDTO(countryCode));
        assertThrows(BankNotFoundException.class, () -> bankService.getBanksByCountryDTO(null));
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
    void testGetHqDTOReturn(){
        String swiftCode = "BREXPLPWXXX";
        HqDTO HqDTO = bankService.getHqDTO(swiftCode);
        assertTrue(HqDTO.isHeadquarter());
        assert(HqDTO.getBranches().size() == 2);
    }

    @Test
    void testGetHqDTOThrowsByWrongSwiftCode(){
        String swiftCode = "jfa321222k";
        assertThrows(BankNotFoundException.class, () -> bankService.getHqDTO(swiftCode));
        String swiftCode2 = "abc";
        assertThrows(WrongSwiftCodeException.class, () -> bankService.getHqDTO(swiftCode2));
        assertThrows(WrongSwiftCodeException.class, () -> bankService.getHqDTO(null));
    }

}

