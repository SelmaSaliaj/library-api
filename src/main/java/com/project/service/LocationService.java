package com.project.service;

import com.project.domain.dto.LocationDTO;
import com.project.filter.Filter;

import java.util.List;

public interface LocationService {

    LocationDTO findById(Integer id);

    //void save(LocationRequest request);

    LocationDTO save(String request);

    //void update(Integer id, LocationRequest request);
    LocationDTO update(Integer id, String request);

    LocationDTO delete(Integer id);

    List<LocationDTO> getAllLocations(Filter... filters);

}
