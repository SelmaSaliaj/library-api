package com.project.controller;

import com.project.domain.dto.BookReservationDTO;
import com.project.domain.dto.ReservationDTO;
import com.project.service.BookReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookReservation")
public class BookReservationController {

    private final BookReservationService bookReservationService;

    @Autowired
    public BookReservationController(BookReservationService bookReservationService) {
        this.bookReservationService = bookReservationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookReservationDTO> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(bookReservationService.findById(id));
    }

    @PostMapping
    public void save(@RequestBody BookReservationDTO bookReservationDTO){
        bookReservationService.save(bookReservationDTO);
    }

    @PutMapping
    public void update(@RequestBody BookReservationDTO bookReservationDTO){
        bookReservationService.update(bookReservationDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        bookReservationService.delete(id);
    }

}
