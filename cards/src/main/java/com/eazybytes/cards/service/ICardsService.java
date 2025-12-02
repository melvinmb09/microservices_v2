package com.eazybytes.cards.service;

import com.eazybytes.cards.dto.CardsDto;

public interface ICardsService {
    void createCards(String mobileNumber);
    CardsDto retreiveCards(String mobileNumber);
    void updateCards(CardsDto cardsDto);
    void deleteCards(String mobileNumber);
}
