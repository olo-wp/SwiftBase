package com.swift.project;

import com.swift.project.repo.BankRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.swift.project" ,"com.swift.project.controllers"})
@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {

        SpringApplication.run(ProjectApplication.class, args);
	}


}
