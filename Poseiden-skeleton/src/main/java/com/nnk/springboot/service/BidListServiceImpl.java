package com.nnk.springboot.service;



import com.nnk.springboot.interfaces.BidListService;
import com.nnk.springboot.model.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;


    @Override
    public void validate(BidList bidList) {

        BidList bid = new BidList();
        bid.setAccount(bidList.getAccount());
        bid.setType(bidList.getType());
        bid.setBidQuantity(bidList.getBidQuantity());
        bid.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        bidListRepository.save(bid);


    }

    @Override
    public void updateBid(Integer id, BidList bidList){
        BidList bidListInDb = bidListRepository.findBidListById(id);

        bidListInDb.setAccount(bidList.getAccount());
        bidListInDb.setType(bidList.getType());
        bidListInDb.setBidQuantity(bidList.getBidQuantity());
        bidListInDb.setRevisionDate(Timestamp.valueOf(LocalDateTime.now()));
        bidListRepository.save(bidListInDb);
    }

    @Override
    public void deleteBid(Integer id) {
        bidListRepository.deleteById(id);
    }
}
