package com.swift.project.service;

import com.swift.project.DTOs.BanksByCountryDTO;
import com.swift.project.DTOs.BranchDTO;
import com.swift.project.DTOs.HqDTO;
import com.swift.project.DTOs.SingleBankDTO;
import com.swift.project.data.BankEntity;
import com.swift.project.exceptions.BankAlreadyInDataBaseException;
import com.swift.project.exceptions.BankNotFoundException;
import com.swift.project.exceptions.WrongSwiftCodeException;
import com.swift.project.other.SwiftCodeMethods;
import com.swift.project.repo.BankRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankService {

    private final BankRepository bankRepository;

    BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    private BankEntity mapSingleBankDTOtoBankEntity(SingleBankDTO singleBankDTO) {
        BankEntity bank = new BankEntity();
        bank.setBankName(singleBankDTO.getBankName());
        bank.setAddress(singleBankDTO.getAddress());
        bank.setCountryISO2(singleBankDTO.getCountryISO2());
        bank.setCountryName(singleBankDTO.getCountryName());
        bank.setIsHeadquarter(singleBankDTO.isHeadquarter());
        bank.setSwiftCode(singleBankDTO.getSwiftCode());
        return bank;
    }

    @Transactional
    public void saveBank(SingleBankDTO singleBankDTO) {
        SwiftCodeMethods.checkSwiftCodeLength(singleBankDTO.getSwiftCode());
        BankEntity bank = mapSingleBankDTOtoBankEntity(singleBankDTO);
        Optional<BankEntity> b = bankRepository.findById(bank.getSwiftCode());
        if (b.isPresent()) throw new BankAlreadyInDataBaseException(bank.getSwiftCode());
        bankRepository.save(bank);
    }

    @Transactional
    public void removeBank(String swiftCode) {
        SwiftCodeMethods.checkSwiftCodeLength(swiftCode);
        Optional<BankEntity> bank = bankRepository.findById(swiftCode);
        if (bank.isEmpty()) throw new BankNotFoundException(swiftCode, "Swift Code");
        bankRepository.deleteById(swiftCode);
    }

    public SingleBankDTO getBank(String swiftCode) {
        SwiftCodeMethods.checkSwiftCodeLength(swiftCode);
        Optional<BankEntity> bank = bankRepository.findById(swiftCode);
        if (bank.isEmpty()) throw new BankNotFoundException(swiftCode, "Swift Code");
        return mapBankToSingleBankDTO(bank.get());
    }

    public long getBankRepositorySize() {
        return bankRepository.count();
    }

    public BanksByCountryDTO getBanksByCountryDTO(String countryISO2) throws BankNotFoundException, WrongSwiftCodeException {
        List<BankEntity> banks = bankRepository.findAllByCountryISO2(countryISO2);
        if (banks.isEmpty()) throw new BankNotFoundException(countryISO2, "countryISO2");

        String b_countryISO2 = banks.get(0).getCountryISO2();
        String b_countryName = banks.get(0).getCountryName();

        List<BranchDTO> banksInCountry = banks.stream()
                .map(this::mapBankToBranchDTO)
                .collect(Collectors.toList());

        return new BanksByCountryDTO(
                b_countryISO2,
                b_countryName,
                banksInCountry
        );
    }

    public HqDTO getHqDTO(String swiftCode) {
        SwiftCodeMethods.checkSwiftCodeLength(swiftCode);
        List<BankEntity> banks = bankRepository.findBySwiftCodeStartingWith(SwiftCodeMethods.getCommonPrefix(swiftCode));

        BankEntity hq = banks.stream().filter(BankEntity::getIsHeadquarter).findFirst()
                .orElseThrow(() -> new BankNotFoundException(swiftCode, "Swift Code"));

        List<BranchDTO> branches = banks.stream().map(bank -> new BranchDTO(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountryISO2(),
                bank.getIsHeadquarter(),
                bank.getSwiftCode()
        )).collect(Collectors.toList());

        return new HqDTO(
                hq.getAddress(),
                hq.getBankName(),
                hq.getCountryISO2(),
                hq.getCountryName(),
                hq.getIsHeadquarter(),
                hq.getSwiftCode(),
                branches
        );
    }

    private BranchDTO mapBankToBranchDTO(BankEntity bank) {
        return new BranchDTO(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountryISO2(),
                bank.getIsHeadquarter(),
                bank.getSwiftCode()
        );
    }

    private SingleBankDTO mapBankToSingleBankDTO(BankEntity bank) {
        return new SingleBankDTO(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountryISO2(),
                bank.getCountryName(),
                bank.getIsHeadquarter(),
                bank.getSwiftCode()
        );
    }
}
