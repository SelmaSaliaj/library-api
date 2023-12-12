package com.project.controller;

import com.project.domain.dto.PhysicalCopyDTO;
import com.project.domain.dto.PhysicalCopyRequest;
import com.project.service.PhysicalCopyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class PhysicalCopyController {

   // @Autowired
    private final PhysicalCopyService bookService;

    @Autowired
    public PhysicalCopyController(PhysicalCopyService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhysicalCopyDTO> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping("/byAuthor")
    public ResponseEntity<List<PhysicalCopyDTO>> findByAuthor(@RequestParam String author){
        List<PhysicalCopyDTO> list = bookService.findByAuthor(author);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/byTitle")
    public ResponseEntity<List<PhysicalCopyDTO>> findByTitle(@RequestParam String title){
        List<PhysicalCopyDTO> list = bookService.findByTitle(title);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/byTitleAndAuthor")
    public ResponseEntity<PhysicalCopyDTO> findByTitleAndAuthor(@RequestParam String title, @RequestParam String author){
        PhysicalCopyDTO book = bookService.findByTitleAndAuthor(title,author);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public void save(@RequestBody @Valid PhysicalCopyRequest physicalCopy){
        bookService.save(physicalCopy);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody @Valid PhysicalCopyRequest physicalCopy){
        bookService.update(id,physicalCopy);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        bookService.delete(id);
    }

}
