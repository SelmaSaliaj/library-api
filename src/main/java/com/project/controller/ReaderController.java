package com.project.controller;

import com.project.domain.dto.ReaderDTO;
import com.project.domain.dto.ReaderRequest;
import com.project.service.ReaderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reader")
public class ReaderController {

    private final ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderDTO> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(readerService.findById(id));
    }

    @PostMapping
    public void save(@RequestBody @Valid ReaderRequest reader){
        readerService.save(reader);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Integer id, @RequestBody @Valid ReaderRequest reader){
        readerService.update(id, reader);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        readerService.delete(id);
    }


}
