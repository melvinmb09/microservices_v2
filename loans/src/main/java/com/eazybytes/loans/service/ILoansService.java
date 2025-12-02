package com.eazybytes.loans.service;

import com.eazybytes.loans.dto.LoansDto;

public interface ILoansService {
    public void createLoan(String mobileNumber);
    public LoansDto fetchLoanDto(String mobileNumber);
    public boolean updateLoans(LoansDto loansDto);
    public boolean deleteLoans(String mobileNumber);
}
