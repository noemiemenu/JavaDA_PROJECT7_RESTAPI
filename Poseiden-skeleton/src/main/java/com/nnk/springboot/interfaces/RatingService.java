package com.nnk.springboot.interfaces;

import com.nnk.springboot.exception.NegativeNumberException;
import com.nnk.springboot.model.Rating;

public interface RatingService {

    void updateRating(Integer id, Rating rating) throws NegativeNumberException;

    void validateRating(Rating rating) throws NegativeNumberException;

    void deleteRating(Integer id);

}
