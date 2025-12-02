package com.eazybytes.loans.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoansDto {

    @NotEmpty(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotEmpty(message = "Loan number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{12})",message = "LoanNumber must be 12 digits")
    private String loanNumber;

    @NotEmpty(message = "Loan type can not be a null or empty")
    private String loanType;

    @Positive(message = "Total loan amount should be a positive number")
    private int totalLoan;

    @PositiveOrZero(message = "Amount paid should be a positive number or zero")
    private int amountPaid;

    @PositiveOrZero(message = "Outstanding amount should be a positive number or Zero")
    private int outstandingAmount;
}
