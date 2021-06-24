package com.nnk.springboot.interfaces;


import com.nnk.springboot.model.BidList;


public interface BidListService {

    void validate(BidList bidList) ;

    void updateBid(Integer id, BidList bidList) ;

    void deleteBid(Integer id);
}
