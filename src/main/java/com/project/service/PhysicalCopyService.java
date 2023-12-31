package com.project.service;

import com.project.domain.dto.PhysicalCopyDTO;
import com.project.domain.dto.PhysicalCopyRequest;
import com.project.filter.Filter;

import java.util.List;

public interface PhysicalCopyService{

    PhysicalCopyDTO findById(Integer id);

    PhysicalCopyDTO save(PhysicalCopyRequest request);

    PhysicalCopyDTO update(Integer id, PhysicalCopyRequest request);

    PhysicalCopyDTO delete(Integer id);

    List<PhysicalCopyDTO> findByTitle(String title);

    List<PhysicalCopyDTO> findByAuthor(String author);

    PhysicalCopyDTO findByTitleAndAuthor(String title, String author);

    List<PhysicalCopyDTO> getAllPhysicalBooks(Filter... filters);

}
