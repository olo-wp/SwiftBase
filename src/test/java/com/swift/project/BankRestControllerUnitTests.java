package com.swift.project;


import com.swift.project.controllers.BankRestController;
import com.swift.project.service.BankService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
public class BankRestControllerUnitTests {
    @InjectMocks
    private BankRestController bankRestController;

    @Mock
    private BankService bankService;

    @Test
    void testGetBankWhenBankExists(){

    }
}


