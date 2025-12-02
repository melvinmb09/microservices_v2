package com.eazybytes.cards.service.Impl;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardsMapper;
import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImplementation implements ICardsService {

    private CardsRepository cardsRepository;

    @Override
    public void createCards(String mobileNumber) {

        Optional<Cards> cards = cardsRepository.findByMobileNumber(mobileNumber);
        if(cards.isPresent()) {
            throw new CardAlreadyExistsException("Card already exists with the given mobile number: " + mobileNumber);
        }
        newCard(mobileNumber);
    }

    public void newCard(String mobileNumber){
        Cards card = new Cards();
        card.setMobileNumber(mobileNumber);
        long randomNumber = 100000000000l + new Random().nextInt(900000000);
        card.setCardNumber(Long.toString(randomNumber));
        card.setCardType(CardsConstants.CREDIT_CARD);
        card.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        card.setAmountUsed(50000);
        card.setAvailableAmount(50000);

        cardsRepository.save(card);
    }

    @Override
    public CardsDto retreiveCards(String mobileNumber){
        Cards card = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->  new ResourceNotFoundException("Cards","mobileNumber",mobileNumber));
        CardsDto cardsDto = CardsMapper.cardsToDto(new CardsDto(), card);
        return cardsDto;
    }

    @Override
    public void updateCards(CardsDto cardsDto) {
        Cards card = cardsRepository.findByMobileNumber(cardsDto.getMobileNumber())
                .orElseThrow(() ->  new ResourceNotFoundException("Cards","mobileNumber",cardsDto.getMobileNumber()));

        Cards cards = CardsMapper.dtoToCards(cardsDto, new Cards());
    }

    @Override
    public void deleteCards(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->  new ResourceNotFoundException("Cards","mobileNumber",mobileNumber));
        cardsRepository.delete(card);
    }


}

