package com.swift.project.integrationTests;


import com.swift.project.DTOs.SingleBankDTO;
import com.swift.project.repo.BankRepository;
import com.swift.project.service.BankService;
import com.swift.project.service.XlsxParseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class BankServiceIntegrationTests {
    @MockitoBean
    XlsxParseService xlsxParseService;

    @Autowired
    BankService bankService;

    @Autowired
    BankRepository bankRepository;

    @Test
    void testBankServiceAddChangesStateOfRepository() {
        SingleBankDTO singleBankDTO = new SingleBankDTO(
                " ",
                "Rich People Bank",
                "LI",
                "LIECHTENSTEIN",
                true,
                "12345678XXX"
        );
        bankService.addBank(singleBankDTO);
        assertTrue(bankRepository.existsById("12345678XXX"));
    }

    @Test
    void testBankServiceDeleteChangesStateOfRepository() {
        bankService.removeBank("CBARAWAWXXX");
        assertFalse(bankRepository.existsById("CBARAWAWXXX"));
    }
}
