package com.nnk.springboot.interfaces;


import com.nnk.springboot.exception.NegativeNumberException;
import com.nnk.springboot.model.BidList;


public interface BidListService {

    void validate(BidList bidList) throws NegativeNumberException;

    void updateBid(Integer id, BidList bidList) throws NegativeNumberException;

    void deleteBid(Integer id);
}
