package com.eazybytes.cards.controller;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardsContactInfoDto;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.dto.ResponseDto;
import com.eazybytes.cards.service.ICardsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {

    private final ICardsService cardService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;


    public CardsController(ICardsService cardService){
        this.cardService = cardService;
    }

    @PostMapping(path= "/create")
    public ResponseEntity<ResponseDto> createCards(@Valid @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                            String mobileNumber){

        cardService.createCards(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));

    }

    @GetMapping(path= "/fetch")
    public ResponseEntity<CardsDto> fetchCards(@Valid @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                    String mobileNumber){
        CardsDto cardsDto = cardService.retreiveCards(mobileNumber);

        return new ResponseEntity<>(cardsDto, HttpStatus.CREATED);
    }

    @PutMapping(path ="/update")
    public ResponseEntity<ResponseDto> updateCards(@Valid @RequestBody CardsDto cardsDto){
        cardService.updateCards(cardsDto);
        return new ResponseEntity<>(new ResponseDto(CardsConstants.STATUS_200,
                CardsConstants.MESSAGE_200),
                HttpStatus.OK);
    }

    @DeleteMapping(path="/delete")
    public ResponseEntity<ResponseDto> deleteCards(@Valid @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                       String mobileNumber){
        cardService.deleteCards(mobileNumber);
        return new ResponseEntity<>(new ResponseDto(CardsConstants.STATUS_200,
                CardsConstants.MESSAGE_200),
                HttpStatus.OK);
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
    public ResponseEntity<CardsContactInfoDto> fetchContactInfo(){
        return ResponseEntity.
                status(HttpStatus.OK).
                body(cardsContactInfoDto);
    }
}
