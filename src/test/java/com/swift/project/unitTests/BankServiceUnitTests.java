package com.swift.project.unitTests;

import com.swift.project.DTOs.BranchDTO;
import com.swift.project.DTOs.HqDTO;
import com.swift.project.DTOs.SingleBankDTO;
import com.swift.project.data.BankEntity;
import com.swift.project.exceptions.BankAlreadyInDataBaseException;
import com.swift.project.exceptions.BankNotFoundException;
import com.swift.project.exceptions.IllegalSwiftCodeException;
import com.swift.project.repo.BankRepository;
import com.swift.project.service.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BankServiceUnitTests {

    private BankRepository bankRepository;

    private BankService bankService;

    @BeforeEach
    void setup() {
        bankRepository = mock(BankRepository.class);
        bankService = new BankService(bankRepository);
    }

    @Test
    public void testSaveBank() {
        SingleBankDTO singleBankDTOHq = new SingleBankDTO(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                "POLAND",
                true,
                "SWIFTXXX");

        BankEntity entity = new BankEntity(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                "POLAND",
                true,
                "SWIFTXXX");

        bankService.addBank(singleBankDTOHq);

        when(bankRepository.findById("SWIFTXXX")).thenReturn(Optional.empty());
        when(bankRepository.save(any())).thenReturn(entity);

        verify(bankRepository, times(1)).findById("SWIFTXXX");
        verify(bankRepository, times(1)).save(entity);
    }

    @Test
    public void saveBankException(){
        BankEntity entity = new BankEntity(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                "POLAND",
                true,
                "SWIFTXXX");
        SingleBankDTO singleBankDTOHq = new SingleBankDTO(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                "POLAND",
                true,
                "SWIFTXXX");

        when(bankRepository.findById("SWIFTXXX")).thenReturn(Optional.of(entity));
        assertThrows(BankAlreadyInDataBaseException.class,() -> bankService.addBank(singleBankDTOHq));

    }

    @Test
    public void testFindBankException(){
        when(bankRepository.findById("DOESNTEXIST")).thenReturn(Optional.empty());
        assertThrows(BankNotFoundException.class, () -> bankService.getBank("DOESNTEXIST"));
        assertThrows(IllegalSwiftCodeException.class, () -> bankService.getBank("DO"));
    }

    @Test
    public void testDeleteBank() {
        BankEntity entity = new BankEntity(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                "POLAND",
                true,
                "SWIFTXXX");

        when(bankRepository.findById("SWIFTXXX")).thenReturn(Optional.of(entity));

        bankService.removeBank("SWIFTXXX");

        verify(bankRepository, times(1)).findById("SWIFTXXX");

        verify(bankRepository, times(1)).deleteById("SWIFTXXX");
    }

    @Test
    public void testDeleteBankException(){
        when(bankRepository.findById("SWIFTXXX")).thenReturn(Optional.empty());
        assertThrows(BankNotFoundException.class, () -> bankService.removeBank("SWIFTXXX"));
    }

    @Test
    public void testFindBank() {
        SingleBankDTO singleBankDTOHq = new SingleBankDTO(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                "POLAND",
                true,
                "SWIFTXXX");

        BankEntity entity = new BankEntity(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                "POLAND",
                true,
                "SWIFTXXX");

        when(bankRepository.findById("SWIFTXXX")).thenReturn(Optional.of(entity));
        assertEquals(singleBankDTOHq, bankService.getBank("SWIFTXXX"));
    }

    @Test
    public void testGetHqDTOException(){
        when(bankRepository.findById("SWIFTXXX")).thenReturn(Optional.empty());
        assertThrows(BankNotFoundException.class, () ->bankService.getHqDTO("SWIFTXXX"));
        assertThrows(IllegalSwiftCodeException.class, () -> bankService.getHqDTO("abc"));
    }

    @Test
    public void testGetHqDTO(){
        BranchDTO branchDTO = new BranchDTO(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                false,
                "SWIFTBRA"
        );
        BranchDTO branchDTO2 = new BranchDTO(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                true,
                "SWIFTBRAXXX"
        );
        List<BranchDTO> branchDTOList = new ArrayList<>();
        branchDTOList.add(branchDTO2);
        branchDTOList.add(branchDTO);
        HqDTO hqDTO = new HqDTO(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                "POLAND",
                true,
                "SWIFTBRAXXX",
                branchDTOList
        );
        BankEntity entity1 = new BankEntity(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                "POLAND",
                true,
                "SWIFTBRAXXX");
        BankEntity entity2 = new BankEntity(
                "Jazmowa 14",
                "testbankhq",
                "PL",
                "POLAND",
                false,
                "SWIFTBRA");

        when(bankRepository.findBySwiftCodeStartingWith("SWIFTBRA")).thenReturn(List.of(entity1,entity2));
        assertEquals(hqDTO, bankService.getHqDTO("SWIFTBRAXXX"));

    }


}
