package com.nnk.springboot;

import com.nnk.springboot.exception.NegativeNumberException;
import com.nnk.springboot.interfaces.RatingService;
import com.nnk.springboot.model.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private RatingService ratingService;

	@Test
	public void ratingTest() {
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Save
		rating = ratingRepository.save(rating);
		Assert.assertNotNull(rating.getId());
		Assert.assertTrue(rating.getOrderNumber() == 10);

		// Update
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
		Assert.assertTrue(rating.getOrderNumber() == 20);

		// Find
		List<Rating> listResult = ratingRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		Assert.assertFalse(ratingList.isPresent());
	}

	@Test
	public void deleteRatingTest() {
		Rating rating = new Rating("moodys","sand","fitch",10);
		rating = ratingRepository.save(rating);
		// Delete
		Integer id = rating.getId();
		ratingService.deleteRating(id);
		Optional<Rating> ratingId = ratingRepository.findById(id);
		Assert.assertFalse(ratingId.isPresent());


	}

	@Test
	public void updateRatingTest() {
		//given
		Rating rating = new Rating("moodys","sand","fitch",10);
		rating = ratingRepository.save(rating);
		Integer id = rating.getId();

		Rating updateRating = new Rating("moodysTest","sandTest","fitchTest",20);

		//when
		assertDoesNotThrow(() -> ratingService.updateRating(id, updateRating));

		//then
		Assert.assertEquals(rating.getMoodysRating(),"moodysTest", "moodysTest");
	}

	@Test
	public void validateRatingTest() {
		//given
		Rating addRating = new Rating("moodysTest","sandTest","fitchTest",20);

		//when
		assertDoesNotThrow(() -> ratingService.validateRating(addRating));

		//then
		Rating ratingId = ratingRepository.findIdByMoodysRatingAndOrderNumber("moodysTest", 20);
		Optional<Rating> rating = ratingRepository.findById(ratingId.getId());
		Assert.assertTrue(rating.isPresent());
	}

	@Test
	public void validateRatingTest_Throw_NegativeNumberException() {
		Rating addRating = new Rating("moodysTest","sandTest","fitchTest",-20);

		assertThrows(NegativeNumberException.class, () ->ratingService.validateRating(addRating));
	}

	@Test
	public void updateRatingTest_Throw_NegativeNumberException() {
		Rating rating = new Rating("moodys","sand","fitch",10);
		rating = ratingRepository.save(rating);
		Integer id = rating.getId();

		Rating updateRating = new Rating("moodysTest","sandTest","fitchTest",-20);

		assertThrows(NegativeNumberException.class, () ->ratingService.updateRating(id, updateRating));

	}

}
