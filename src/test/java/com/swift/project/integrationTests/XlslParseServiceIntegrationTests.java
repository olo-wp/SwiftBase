package com.swift.project.integrationTests;

import com.swift.project.other.constants;
import com.swift.project.repo.BankRepository;
import com.swift.project.service.XlsxParseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class XlslParseServiceIntegrationTests {
    @Autowired
    BankRepository bankRepository;

    @Autowired
    XlsxParseService xlsxParseService;

    /* we want to get rid of entries from test-data.sql */
    @BeforeEach
    void clearDatabase() {
        bankRepository.deleteAll();
        xlsxParseService.importXlsxOnStart();
    }

    @Test
    void testRepositoryIsParsedFromTheXlsxFile() {
        assertEquals(constants.SWIFT_FILE_ENTRIES, bankRepository.count());
    }

    @Test
    void testDataFromTheXlsxFileInTheDatabase() {
        assertTrue(bankRepository.existsById("CRLYMCM1LCM"));
        assertTrue(bankRepository.existsById("DBINMTM1XXX"));
    }

}
