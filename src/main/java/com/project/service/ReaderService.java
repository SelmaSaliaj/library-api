package com.project.service;

import com.project.domain.dto.ReaderDTO;
import com.project.domain.dto.ReaderRequest;
import com.project.filter.Filter;

import java.util.List;

public interface ReaderService {

    ReaderDTO findById(Integer id);

    //the two find by id need to return a value for the sake of the program
    ReaderDTO findByIdAndDeletedValueFalse(Integer id);

    void findByIdAndDeletedValueTrue(Integer id);

    ReaderDTO save(ReaderRequest request);

    ReaderDTO update(Integer id, ReaderRequest request);

    ReaderDTO delete(Integer id);

    ReaderDTO softDelete(Integer id);

    ReaderDTO restore(Integer id);

    List<ReaderDTO> getAllReaders(Filter... filters);

}
