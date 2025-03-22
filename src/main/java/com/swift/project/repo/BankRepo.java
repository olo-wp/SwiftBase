package com.swift.project.repo;

import com.swift.project.data.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankRepo extends JpaRepository<BankEntity, String> {
    List<BankEntity> findAllByISO2(String ISO2);


}
