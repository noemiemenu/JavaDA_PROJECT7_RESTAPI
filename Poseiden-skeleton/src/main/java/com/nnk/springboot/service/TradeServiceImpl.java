package com.nnk.springboot.service;

import com.nnk.springboot.exception.NegativeNumberException;
import com.nnk.springboot.interfaces.TradeService;
import com.nnk.springboot.model.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;

    @Override
    public void validateTrade(Trade trade) throws NegativeNumberException {
        if (trade.getBuyQuantity() < 0){
            throw new NegativeNumberException("Buy Quantity cannot be negative");
        }

        Trade addTrade = new Trade();
        addTrade.setAccount(trade.getAccount());
        addTrade.setType(trade.getType());
        addTrade.setBuyQuantity(trade.getBuyQuantity());
        addTrade.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        tradeRepository.save(addTrade);
    }

    @Override
    public void updateTrade(Integer id, Trade trade) throws NegativeNumberException {
        if (trade.getBuyQuantity() < 0){
            throw new NegativeNumberException("Buy Quantity cannot be negative");
        }
        Trade tradeInDb = tradeRepository.findTradeById(id);
        tradeInDb.setAccount(trade.getAccount());
        tradeInDb.setType(trade.getType());
        tradeInDb.setBuyQuantity(trade.getBuyQuantity());
        tradeRepository.save(tradeInDb);
    }

    @Override
    public void deleteTrade(Integer id) {
        tradeRepository.deleteById(id);
    }
}
