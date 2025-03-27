package com.swift.project.controllers;

import com.swift.project.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    public HomeController(BankService bankService) {
        System.out.println("HOME CONTROL START");
    }

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    @RequestMapping("/test")
    public String index_test() {return "index_test.html";}

}


