package com.eazybytes.loans.controller;

import com.eazybytes.loans.constants.LoanConstants;
import com.eazybytes.loans.dto.LoansContactInfoDto;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.dto.ResponseDto;
import com.eazybytes.loans.service.ILoansService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class LoansController {

    private ILoansService loanService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private LoansContactInfoDto cardsContactInfoDto;

    public LoansController(ILoansService loanService){
        this.loanService = loanService;
    }


    @PostMapping (path = "/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam
                                                      @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                      @Valid String mobileNumber) {
        loanService.createLoan(mobileNumber);
        return ResponseEntity.ok(new ResponseDto(LoanConstants.MESSAGE_201,LoanConstants.STATUS_201));
    }

    @GetMapping (path = "/fetch")
    public ResponseEntity<LoansDto> retreiveLoan(@RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                     @Valid String mobileNumber){
        LoansDto loansDto = loanService.fetchLoanDto(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }

    @PutMapping (path = "/update")
    public ResponseEntity<ResponseDto> updateLoan(@RequestBody @Valid LoansDto loansDto){
        boolean updated = loanService.updateLoans(loansDto);
        if (updated) {
            return ResponseEntity.ok(new ResponseDto(LoanConstants.MESSAGE_200,LoanConstants.STATUS_200));
        }
        else{
           return ResponseEntity
                   .status(HttpStatus.EXPECTATION_FAILED)
                   .body(new ResponseDto(LoanConstants.MESSAGE_417_UPDATE,LoanConstants.STATUS_417));
        }
    }

    @DeleteMapping (path = "/delete")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam @Valid String mobileNumber){
        boolean deleted = loanService.deleteLoans(mobileNumber);
        if (deleted) {
            return ResponseEntity.ok(new ResponseDto(LoanConstants.MESSAGE_200,LoanConstants.STATUS_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.MESSAGE_417_DELETE,LoanConstants.STATUS_417));
        }
    }

    @GetMapping(path="/build-info")
    public ResponseEntity<String> fetchBuildInfo(){
        return ResponseEntity.
                status(HttpStatus.OK).
                body(buildVersion);
    }

    @GetMapping(path="/java-version")
    public ResponseEntity<String> fetchJavaVersion(){
        return ResponseEntity.
                status(HttpStatus.OK).
                body(environment.getProperty("JAVA-HOME"));
    }

    @GetMapping(path="/contact-info")
    public ResponseEntity<LoansContactInfoDto> fetchContactInfo(){
        return ResponseEntity.
                status(HttpStatus.OK).
                body(cardsContactInfoDto);
    }



}
