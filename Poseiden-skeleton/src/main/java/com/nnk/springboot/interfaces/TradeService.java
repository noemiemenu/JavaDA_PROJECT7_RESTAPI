package com.nnk.springboot.interfaces;

import com.nnk.springboot.exception.NegativeNumberException;
import com.nnk.springboot.model.Trade;

public interface TradeService {
    void validateTrade(Trade trade) throws NegativeNumberException;

    void updateTrade(Integer id, Trade trade) throws NegativeNumberException;

    void deleteTrade(Integer id);
}
