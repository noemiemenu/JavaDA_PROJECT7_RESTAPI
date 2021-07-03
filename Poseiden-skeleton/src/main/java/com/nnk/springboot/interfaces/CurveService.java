package com.nnk.springboot.interfaces;


import com.nnk.springboot.exception.NegativeNumberException;
import com.nnk.springboot.model.CurvePoint;

public interface CurveService {
    void validateCurvePoint(CurvePoint curvePoint) throws NegativeNumberException;
    void updateCurvePoint(Integer id, CurvePoint curvePoint) throws NegativeNumberException;
    void deleteCurvePoint(Integer id);
}
