package com.project.service.impl;

import com.project.domain.dto.LocationDTO;
import com.project.domain.entity.LocationEntity;
import com.project.domain.exception.MethodCanNotBePerformedException;
import com.project.domain.mapper.LocationMapper;
import com.project.filter.Filter;
import com.project.repository.LocationRepository;
import com.project.service.LocationService;
import com.project.util.ValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    //@Autowired
    private final LocationRepository repository;

    @Autowired
    public LocationServiceImpl(LocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public LocationDTO findById(Integer id) {
        return LocationMapper.toDTO(repository.findById(id));
    }

    /*@Transactional
    @Override
    public void save(LocationRequest request) {

        request.setNameOfTheShelf(ValidationUtils.checkIfValueIsNotCorrectlyValidated(request.getNameOfTheShelf(),
                "nameOfTheShelf"));

        ValidationUtils.checkIfLocationWithGivenShelfNameAlreadyExists(request,repository);

        LocationMapper.toDTO(repository.save(LocationMapper.toEntity(request)));
    }*/

    @Transactional
    @Override
    public LocationDTO save(String request) {

        request = ValidationUtils.checkIfValueIsNotCorrectlyValidated(request,
                "nameOfTheShelf");

        ValidationUtils.checkIfLocationWithGivenShelfNameAlreadyExists(request,repository);

        return LocationMapper.toDTO(repository.save(LocationMapper.toEntity(request)));
    }

    @Transactional
    @Override
    public LocationDTO update(Integer id, String request) {

        findById(id);

        request = ValidationUtils.checkIfValueIsNotCorrectlyValidated(request,
                "nameOfTheShelf");

        ValidationUtils.checkIfLocationWithGivenShelfNameAlreadyExists(id, request, repository);

        if(ValidationUtils.areBooksFoundInGivenLocation(id, repository)){
            throw new MethodCanNotBePerformedException("Location with id:" + id +
                    " can not be changed or deleted, there are still books found on that location.");
        }

        LocationDTO locationDTO = LocationMapper.toDTO(LocationMapper.toEntity(request));
        locationDTO.setId(id);

        return LocationMapper.toDTO(repository.update(LocationMapper.toEntity(locationDTO)));
    }

    /*@Transactional
    @Override
    public void update(Integer id, LocationRequest request) {

        findById(id);

        request.setNameOfTheShelf(ValidationUtils.checkIfValueIsNotCorrectlyValidated(request.getNameOfTheShelf(),
                "nameOfTheShelf"));

        ValidationUtils.checkIfLocationWithGivenShelfNameAlreadyExists(id, request, repository);

        if(ValidationUtils.areNoBooksFoundInGivenLocation(id, repository)){
            LocationDTO locationDTO = LocationMapper.toDTO(LocationMapper.toEntity(request));
            locationDTO.setId(id);

            repository.update(LocationMapper.toEntity(locationDTO));
        }

    }*/

    @Transactional
    @Override
    public LocationDTO delete(Integer id) {
        findById(id);
        if(ValidationUtils.areBooksFoundInGivenLocation(id, repository)){
            throw new MethodCanNotBePerformedException("Location with id:" + id +
                    " can not be changed or deleted, there are still books found on that location.");
        }
        return LocationMapper.toDTO(repository.delete(repository.findById(id)));
    }

    @Override
    public List<LocationDTO> getAllLocations(Filter... filters) {
        List<LocationEntity> locations = repository.getAll(filters);
        return locations.stream()
                .map(LocationMapper::toDTO)
                .collect(Collectors.toList());
    }

}
