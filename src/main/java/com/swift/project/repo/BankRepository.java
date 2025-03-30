package com.swift.project.repo;

import com.swift.project.data.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankRepository extends JpaRepository<BankEntity, String> {
    List<BankEntity> findAllByCountryISO2(String countryISO2);

    List<BankEntity> findBySwiftCodeStartingWith(String swiftCodePrefix);


}