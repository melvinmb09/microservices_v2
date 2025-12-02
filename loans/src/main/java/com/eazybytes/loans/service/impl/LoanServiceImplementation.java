package com.eazybytes.loans.service.impl;

import com.eazybytes.loans.constants.LoanConstants;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.exceptions.LoanAlreadyExistsException;
import com.eazybytes.loans.exceptions.ResourceNotFoundException;
import com.eazybytes.loans.mapper.LoansMapper;
import com.eazybytes.loans.repository.LoansRepository;
import com.eazybytes.loans.service.ILoansService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LoanServiceImplementation implements ILoansService {

    private LoansRepository loansRepository;

    public LoanServiceImplementation(LoansRepository loansRepository) {
        this.loansRepository = loansRepository;
    }

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> existingLoans = loansRepository.findByMobileNumber(mobileNumber);
        if (existingLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
        }
        createNewLoan(mobileNumber);
    }

    public void createNewLoan(String mobileNumber){
        Loans loans = new Loans();
        loans.setMobileNumber(mobileNumber);
        Long randomNumber = 100000000000l + new Random().nextInt(900000000);
        loans.setLoanNumber(Long.toString(randomNumber));
        loans.setLoanType(LoanConstants.HOME_LOAN);
        loans.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        loans.setAmountPaid(0);

        loans.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        loansRepository.save(loans);
    }

    @Override
    public LoansDto fetchLoanDto(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber)
                        .orElseThrow(()-> new ResourceNotFoundException("Loan cannot be found with given mobileNumber "
                                + mobileNumber));

        LoansDto loansDto = LoansMapper.mapToLoansDto(new LoansDto(), loans);
        return loansDto;
    }

    @Override
    public boolean updateLoans(LoansDto loansDto) {
        Loans loans = loansRepository.findByMobileNumber(loansDto.getMobileNumber())
                .orElseThrow(()-> new ResourceNotFoundException("Loan cannot be found with given mobileNumber "
                        + loansDto.getMobileNumber()));

        Loans newLoans = LoansMapper.mapToLoans(loansDto,loans);
        loansRepository.save(newLoans);
        return true;
    }

    @Override
    public boolean deleteLoans(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan cannot be found with given mobileNumber "
                        + mobileNumber));
        loansRepository.delete(loans);
        return true;
    }
}
