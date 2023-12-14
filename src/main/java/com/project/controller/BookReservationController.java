package com.project.controller;

import com.project.domain.dto.BookReservationDTO;
import com.project.domain.dto.BookReservationRequest;
import com.project.service.BookReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/bookReservation")
public class BookReservationController {

    private final BookReservationService bookReservationService;

    @Autowired
    public BookReservationController(BookReservationService bookReservationService) {
        this.bookReservationService = bookReservationService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<BookReservationDTO> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(bookReservationService.findByIdAndDeletedValueFalse(id));
    }

    @PostMapping("/{readerId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void save(@PathVariable Integer readerId, @RequestBody BookReservationRequest request){
        bookReservationService.save(readerId,request);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void update(@PathVariable Integer id,
                       @RequestParam("newToReturnDateTime")
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newToReturnDateTime){
       bookReservationService.update(id, newToReturnDateTime);
    }

    @PatchMapping("/statusChange/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateStatusToReturned(@PathVariable Integer id){
        bookReservationService.updateStatusToReturned(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void delete(@PathVariable("id") Integer id){
        bookReservationService.delete(id);
    }

}
