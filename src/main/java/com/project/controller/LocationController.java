package com.project.controller;

import com.project.domain.dto.LocationDTO;
import com.project.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    //@Autowired
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(locationService.findById(id));
    }

    /*@PostMapping
    public void save(@RequestBody @Valid LocationRequest location){
        locationService.save(location);
    }*/

    @PostMapping
    public void save(@RequestParam String nameOfTheShelf){
        locationService.save(nameOfTheShelf);
    }

    /*@PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody @Valid LocationRequest location){
        locationService.update(id, location);
    }*/

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestParam String nameOfTheShelf){
        locationService.update(id, nameOfTheShelf);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        locationService.delete(id);
    }

}
