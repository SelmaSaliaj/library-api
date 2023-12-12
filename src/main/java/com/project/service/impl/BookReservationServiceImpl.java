package com.project.service.impl;

import com.project.domain.dto.BookReservationDTO;
import com.project.domain.dto.PhysicalCopyDTO;
import com.project.domain.exception.MethodCanNotBePerformedException;
import com.project.domain.exception.ResourceNotFoundException;
import com.project.domain.mapper.BookReservationMapper;
import com.project.repository.BookReservationRepository;
import com.project.service.BookReservationService;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookReservationServiceImpl implements BookReservationService {

    //@Autowired
    private final BookReservationRepository repository;

    private static final BookReservationMapper BOOK_RESERVATION_MAPPER = Mappers.getMapper(BookReservationMapper.class);

    @Autowired
    public BookReservationServiceImpl(BookReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookReservationDTO findById(Integer id) {
        return BOOK_RESERVATION_MAPPER.toBookReservationDTO(repository.findById(id));
    }

   /* @Override
    public Page<BookReservationDTO> getAllBookReservations(Pageable pageable) {
        var bookReservationPage = repository.findAll(pageable);
        var content = bookReservationPage.getContent().stream()
                .map(b -> BOOK_RESERVATION_MAPPER.toBookReservationDTO(b))
                .collect(Collectors.toList());
        return new PageImpl<>(content,bookReservationPage.getPageable(),bookReservationPage.getTotalElements());
    }*/

    @Override
    public void save(BookReservationDTO request) {
        int copies = request.getBook().getNumberOfCopiesAvailable();
        if(copies<=0){
            throw new MethodCanNotBePerformedException("You can not reserve the book. No copies available");
        }

        PhysicalCopyDTO book = request.getBook();
        int numberOfCopies = book.getNumberOfCopiesAvailable();
        numberOfCopies -= 1;
        book.setNumberOfCopiesAvailable(numberOfCopies);
        request.setBook(book);

        request.setCreatedDate(LocalDateTime.now());
        request.setLastModified(LocalDateTime.now());
        BOOK_RESERVATION_MAPPER.toBookReservationDTO(repository.save(BOOK_RESERVATION_MAPPER.toBookReservationEntity(request)));
    }

    @Override
    public void update(BookReservationDTO request) {
        request.setLastModified(LocalDateTime.now());
        repository.save(BOOK_RESERVATION_MAPPER.toBookReservationEntity(request,
                repository.findById(request.getId())));
    }

    @Override
    public void delete(Integer id) {
        try {
            repository.delete(repository.findById(id));
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Book Reservation not found with id: " + id);
        }
    }

    @Override
    public Page<BookReservationDTO> getAllBookReservations(Pageable pageable) {
        return null;
    }

}
