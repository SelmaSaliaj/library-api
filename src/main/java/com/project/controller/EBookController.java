package com.project.controller;

import com.project.domain.dto.EBookDTO;
import com.project.domain.dto.EBookRequest;
import com.project.service.EBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EBookController {

    //@Autowired
    private final EBookService eBookService;

    @Autowired
    public EBookController(EBookService eBookService) {
        this.eBookService = eBookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EBookDTO> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(eBookService.findById(id));
    }

    @PostMapping
    public void save(@RequestBody @Valid EBookRequest ebook){
        eBookService.save(ebook);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id,@RequestBody @Valid EBookRequest ebook){
        eBookService.update(id, ebook);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        eBookService.delete(id);
    }

    @GetMapping("/byAuthor")
    public ResponseEntity<List<EBookDTO>> findByAuthor(@RequestParam String author){
        List<EBookDTO> list = eBookService.findByAuthor(author);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/byTitle")
    public ResponseEntity<List<EBookDTO>> findByTitle(@RequestParam String title){
        List<EBookDTO> list = eBookService.findByTitle(title);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/byTitleAndAuthor")
    public ResponseEntity<EBookDTO> findByTitleAndAuthor(@RequestParam String title, @RequestParam String author){
        EBookDTO ebook = eBookService.findByTitleAndAuthor(title,author);
        return ResponseEntity.ok(ebook);
    }

}
