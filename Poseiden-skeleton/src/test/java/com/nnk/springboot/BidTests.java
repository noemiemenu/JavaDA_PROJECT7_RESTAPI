package com.nnk.springboot;


import com.nnk.springboot.exception.NegativeNumberException;
import com.nnk.springboot.interfaces.BidListService;
import com.nnk.springboot.model.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class BidTests {

	@Autowired
	private BidListRepository bidListRepository;

	@Autowired
	private BidListService bidListService;

	@Test
	public void bidListTest() {
		BidList bid = new BidList("Account", "Type Test", 10d);

		// Save
		bid = bidListRepository.save(bid);
		Assert.assertNotNull(bid.getId());
		Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		Assert.assertFalse(bidList.isPresent());
	}

	@Test
	public void deleteBidListTest() {
		BidList bid = new BidList("Account", "Type Test", 10);
		bid = bidListRepository.save(bid);
		// Delete
		Integer id = bid.getId();
		bidListService.deleteBid(id);
		Optional<BidList> bidList = bidListRepository.findById(id);

		Assert.assertFalse(bidList.isPresent());


	}

	@Test
	public void updateBidListTest(){
		//given
		BidList bid = new BidList("Account", "Type Test", 10);
		bid = bidListRepository.save(bid);
		Integer id = bid.getId();

		BidList bidList = new BidList("Account", "Type Test", 20);

		//when
		assertDoesNotThrow(() -> bidListService.updateBid(id, bidList));

		//then
		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);
	}

	@Test
	public void validateBidListTest() {
		//given
		BidList addBidList = new BidList("Account Test", "Type Test", 20);

		//when
		assertDoesNotThrow(() ->bidListService.validate(addBidList));

		//then
		BidList bid = bidListRepository.findBidListIdByAccount("Account Test");
		Optional<BidList> bidList = bidListRepository.findById(bid.getId());
		Assert.assertTrue(bidList.isPresent());
	}

	@Test
	public void validateBidListTest_Throw_NegativeNumberException() {
		//given
		BidList addBidList = new BidList("Account Test", "Type Test", -1);
		//when
		assertThrows(NegativeNumberException.class, () ->bidListService.validate(addBidList));

	}

	@Test
	public void updateBidListTest_Throw_NegativeNumberException() {
		//given
		BidList bid = new BidList("Account", "Type Test", 10);
		bid = bidListRepository.save(bid);
		Integer id = bid.getId();

		BidList bidList = new BidList("Account", "Type Test", -10);

		//when
		assertThrows(NegativeNumberException.class, () -> bidListService.updateBid(id,bidList));

	}
}
