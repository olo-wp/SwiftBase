package com.swift.project.service;

import com.swift.project.data.BankEntity;
import com.swift.project.other.SwiftCodeMethods;
import com.swift.project.repo.BankRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.swift.project.other.constants.CONTENT_SHEET;
import static com.swift.project.other.constants.DOCUMENT_HEAD;


@Service
public class XlsxParseService {
    private final BankRepository bankRepository;

    public XlsxParseService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
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
            Sheet sheet = workbook.getSheetAt(CONTENT_SHEET);
            for(Row row : sheet){
                if (row.getRowNum() == DOCUMENT_HEAD) continue;
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
                bankEntity.setIsHeadquarter(SwiftCodeMethods.representsHQ(bankEntity.getSwiftCode()));
                banks.add(bankEntity);
            }
        }
        bankRepository.saveAll(banks);
    }
}
