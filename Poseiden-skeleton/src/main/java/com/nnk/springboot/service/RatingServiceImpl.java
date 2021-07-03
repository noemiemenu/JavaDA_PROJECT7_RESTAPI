package com.nnk.springboot.service;

import com.nnk.springboot.exception.NegativeNumberException;
import com.nnk.springboot.interfaces.RatingService;
import com.nnk.springboot.model.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Override
    public void validateRating(Rating rating) throws NegativeNumberException {
        if (rating.getOrderNumber() < 0 ){
            throw new NegativeNumberException("Order Number cannot be negative");
        }
        Rating addRating = new Rating();
        addRating.setMoodysRating(rating.getMoodysRating());
        addRating.setSandPRating(rating.getSandPRating());
        addRating.setFitchRating(rating.getFitchRating());
        addRating.setOrderNumber(rating.getOrderNumber());
        ratingRepository.save(addRating);

    }

    @Override
    public void updateRating(Integer id, Rating rating) throws NegativeNumberException {
        if (rating.getOrderNumber() < 0 ){
            throw new NegativeNumberException("Order Number cannot be negative");
        }
        rating.setId(id);
        ratingRepository.save(rating);

    }

    @Override
    public void deleteRating(Integer id) {
       ratingRepository.deleteById(id);
    }
}
