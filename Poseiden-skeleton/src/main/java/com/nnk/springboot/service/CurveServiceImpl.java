package com.nnk.springboot.service;


import com.nnk.springboot.interfaces.CurveService;
import com.nnk.springboot.model.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CurveServiceImpl implements CurveService {
    
    private final CurvePointRepository curvePointRepository;


    
    @Override
    public void validateCurvePoint(CurvePoint curvePoint){

        CurvePoint addCurvePoint = new CurvePoint();
        addCurvePoint.setCurveId(curvePoint.getCurveId());
        addCurvePoint.setTerm(curvePoint.getTerm());
        addCurvePoint.setValue(curvePoint.getValue());
        addCurvePoint.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        curvePointRepository.save(addCurvePoint);
    }

    @Override
    public void updateCurvePoint(Integer id, CurvePoint curvePoint) {
        CurvePoint curvePointInDb = curvePointRepository.findCurvePointById(id);
        curvePointInDb.setCurveId(curvePoint.getCurveId());
        curvePointInDb.setTerm(curvePoint.getTerm());
        curvePointInDb.setValue(curvePoint.getValue());
        curvePointRepository.save(curvePointInDb);
    }

    @Override
    public void deleteCurvePoint(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
