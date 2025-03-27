package com.swift.project.service;

import com.swift.project.data.BankEntity;
import com.swift.project.repo.BankRepo;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class XlsxParseService {
    private final BankRepo bankRepo;

    @Autowired
    public XlsxParseService(BankRepo bankRepo){
        this.bankRepo = bankRepo;
    }

    @PostConstruct
    public void importXlsxOnStart(){
        try{
            importXlsx("Interns_2025_SWIFT_CODES.xlsx");
            System.out.println("import succesful");
        } catch (Exception e){
            System.out.println("Import failed: " + e.getMessage());
        }
    }

    @Transactional
    public void importXlsx(String filePath) throws IOException {
        List<BankEntity> banks = new ArrayList<>();

        InputStream is = new ClassPathResource(filePath).getInputStream();
        try(Workbook workbook = new XSSFWorkbook(is)){
            Sheet sheet = workbook.getSheetAt(0);
            for(Row row : sheet){
                if(row.getRowNum() == 0) continue;
                BankEntity bankEntity = new BankEntity();
                try {
                    bankEntity.setCountryISO2(row.getCell(0).getStringCellValue().strip());
                    bankEntity.setSwiftCode(row.getCell(1).getStringCellValue().strip());
                    bankEntity.setCountryName(row.getCell(6).getStringCellValue().strip());
                    bankEntity.setBankName(row.getCell(3).getStringCellValue().strip());
                    bankEntity.setAddress(row.getCell(4).getStringCellValue().strip());
                } catch (NullPointerException e){
                    continue;
                }
                boolean ishq = bankEntity.getSwiftCode().endsWith("XXX");
                bankEntity.setIsHeadquater(ishq);
                banks.add(bankEntity);
            }
        }
        bankRepo.saveAll(banks);
    }
}
