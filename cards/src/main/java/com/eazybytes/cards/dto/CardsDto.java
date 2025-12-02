package com.eazybytes.cards.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CardsDto {

    @NotNull(message= "Mobile number cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotNull(message= "Card number cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Card number must be than 12 digits")
    private String cardNumber;

    @NotNull(message= "Card type cannot be empty")
    private String cardType;

    @Positive(message= "Total limit must be a positive value")
    private int totalLimit;

    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    private int amountUsed;

    @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    private int availableAmount;
}
