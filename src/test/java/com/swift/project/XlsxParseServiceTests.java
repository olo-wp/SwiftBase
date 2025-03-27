package com.swift.project;

import com.swift.project.repo.BankRepo;
import com.swift.project.service.BankService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class XlsxParseServiceTests {

    @Autowired
    XlsxParseServiceTests(BankService bankService){
        this.bankService = bankService;
    }
    public BankService bankService;
    @Test
    void testPostConstructParsing(){
        assert bankService.getBankRepoSize() == 1061;
        /*maybe this is not the most efficient test, as it uses constant
        and so on, but it will do for now
        */
    }
}
