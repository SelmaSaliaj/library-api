package com.project.controller;

import com.project.domain.dto.ReservationDTO;
import com.project.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @PostMapping("/save/{readerId}")
    public void save(@PathVariable Integer readerId){
        reservationService.save(readerId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        reservationService.delete(id);
    }

}
