package com.swift.project;

import com.swift.project.data.BankEntity;
import com.swift.project.repo.BankRepo;
import com.swift.project.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final BankService bankService;

    @Autowired
    public HomeController(BankService bankService) {
        this.bankService = bankService;
    }

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

}


