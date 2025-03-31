package com.swift.project.unitTests;


import com.swift.project.DTOs.*;
import com.swift.project.controllers.BankRestController;
import com.swift.project.service.BankService;
import com.swift.project.service.XlsxParseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
public class BankRestControllerUnitTests {
    @InjectMocks
    private BankRestController bankRestController;
    @MockitoBean
    private XlsxParseService xlsxParseService;

    @Mock
    private BankService bankService;

    private SingleBankDTO singleBankDTOHq;
    private SingleBankDTO singleBankDTOBranch;
    private BranchDTO branchDTO;
    private HqDTO hqDTO;

    @BeforeEach
    void setUp() {

        singleBankDTOHq = new SingleBankDTO(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                "POLAND",
                true,
                "TESTSWIFTXXX"
        );
        singleBankDTOBranch = new SingleBankDTO(
                "Jazmowa 14",
                "testbank_branch",
                "PL",
                "POLAND",
                false,
                "TESTSWIFT"
        );
        branchDTO = new BranchDTO(
                "Jazmowa 14",
                "testbank_branch",
                "PL",
                false,
                "TESTSWIFT"
        );
        List<BranchDTO> branchDTOList = new ArrayList<>();
        branchDTOList.add(branchDTO);
        hqDTO = new HqDTO(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                "POLAND",
                true,
                "TESTSWIFTXXX",
                branchDTOList
        );
    }

    @Test
    void testGetBankWhenBankExists() {
        when(bankService.getBank("TESTSWIFT")).thenReturn(singleBankDTOBranch);
        ResponseEntity<Object> response = bankRestController.getBank("TESTSWIFT");
        assertEquals(200, response.getStatusCode().value());
        assertEquals(singleBankDTOBranch, response.getBody());
    }

    @Test
    void testGetBankWhenBankIsHeadquarter() {
        when(bankService.getHqDTO("TESTSWIFTXXX")).thenReturn(hqDTO);
        when(bankService.getBank("TESTSWIFTXXX")).thenReturn(singleBankDTOHq);

        ResponseEntity<Object> response = bankRestController.getBank("TESTSWIFTXXX");
        assertEquals(200, response.getStatusCode().value());
        assertEquals(hqDTO, response.getBody());
    }

    @Test
    void testDeleteBank() {
        ResponseEntity<Object> response = bankRestController.delBank("TESTSWIFT");
        verify(bankService, times(1)).removeBank("TESTSWIFT");
        assertEquals(200, response.getStatusCode().value());
        assertInstanceOf(Message.class, response.getBody());
    }

    @Test
    void testAddBank() {
        SingleBankDTO newSingleBankDTO = new SingleBankDTO(
                "Jazmowa 14",
                "testbank_add_branch",
                "PL",
                "POLAND",
                false,
                "TESTSWIFTADD"
        );
        ResponseEntity<Object> response = bankRestController.addBank(newSingleBankDTO);
        verify(bankService, times(1)).saveBank(newSingleBankDTO);
        assertEquals(200, response.getStatusCode().value());
        assertInstanceOf(Message.class, response.getBody());
    }

    @Test
    void testAllBanksByCountryCode() {
        List<BranchDTO> branchDTOList = new ArrayList<>();
        branchDTOList.add(branchDTO);
        BanksByCountryDTO banksByCountryDTO = new BanksByCountryDTO(
                "PL",
                "POLAND",
                branchDTOList
        );
        when(bankService.getBanksByCountryDTO("PL")).thenReturn(banksByCountryDTO);
        ResponseEntity<Object> response = bankRestController.allBanksByCountryCode("PL");
        assertEquals(200, response.getStatusCode().value());
        assertEquals(banksByCountryDTO, response.getBody());
    }
}


