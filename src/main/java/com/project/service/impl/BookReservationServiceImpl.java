package com.project.service.impl;

import com.project.domain.dto.BookReservationDTO;
import com.project.domain.dto.BookReservationRequest;
import com.project.domain.dto.PhysicalCopyDTO;
import com.project.domain.dto.ReservationDTO;
import com.project.domain.entity.BookReservationEntity;
import com.project.domain.enums.BookReservationStatus;
import com.project.domain.exception.MethodCanNotBePerformedException;
import com.project.domain.exception.ResourceAlreadyExistsException;
import com.project.domain.exception.ResourceNotFoundException;
import com.project.domain.exception.ValueNotSupportedException;
import com.project.repository.BookReservationRepository;
import com.project.service.BookReservationService;
import com.project.service.PhysicalCopyService;
import com.project.service.ReservationService;
import com.project.util.Constants;
import com.project.util.ValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookReservationServiceImpl implements BookReservationService {

    private final BookReservationRepository repository;

    private final ReservationService reservationService;

    private final PhysicalCopyService bookService;

    @Autowired
    public BookReservationServiceImpl(BookReservationRepository repository, ReservationService reservationService,
                                      PhysicalCopyService bookService) {
        this.repository = repository;
        this.reservationService = reservationService;
        this.bookService = bookService;
    }

    @Override
    public BookReservationDTO findById(Integer id) {
        return Constants.BOOK_RESERVATION_MAPPER.toBookReservationDTO(repository.findById(id));
    }

    @Override
    public BookReservationDTO findByIdAndDeletedValueFalse(Integer id) {
        return Constants.BOOK_RESERVATION_MAPPER.toBookReservationDTO(repository.findByIdAndDeletedValueFalse(id));
    }

    @Transactional
    @Override
    public void save(Integer readerId, BookReservationRequest request) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        //check if given returned date is valid, it follows a certain pattern
        //check if the data given is valid
        ValidationUtils.checkIfValueIsNotCorrectlyValidated(request.getReturnedDate(),currentDateTime,20);

        ReservationDTO reservation = reservationService.findReservationsByReaderIdAndLocalDate(readerId, currentDateTime);
        PhysicalCopyDTO book = bookService.findByTitleAndAuthor(request.getTitle(), request.getAuthor());

        List<BookReservationEntity> bookReservations = repository.findBookReservationsByReservationId(reservation.getId());
        if(bookReservations != null){
            for (BookReservationEntity bookReservation: bookReservations) {
                if(Objects.equals(bookReservation.getBook().getId(), book.getId())){
                    throw new ResourceAlreadyExistsException("The book reservation for book with " +
                            "title: " + book.getTitle() + " and author: " + book.getAuthor() +
                            " already exists in reservation with id: " + reservation.getId());
                }
            }
        }

        //maybe you can check if this book was also reserved before and it has not been returned

        //we have to update the book, create a new update method in the service layer that takes
        //as a parameter a book dto and update the number of copies available and it should be void (?)
        if (book.getNumberOfCopiesAvailable() == 0){
            throw new ValueNotSupportedException("Book with title: " + book.getTitle() + " and author: " +
                   book.getAuthor() + " has no copies available to book.");
        }
        int newNumberOfAvailableCopies = book.getNumberOfCopiesAvailable() - 1;
        book.setNumberOfCopiesAvailable(newNumberOfAvailableCopies);
        book = bookService.update(book);

        //change the modified date on reservation, you have to create another update method to do that
        reservation.setLastModified(currentDateTime);
        reservation = reservationService.update(reservation);

        //after that create a book reservation dto and set the reservation, the updated book
        // as well as the set dates values and with status RESERVED
        // set changed to 0
        BookReservationDTO bookReservation = new BookReservationDTO();
        bookReservation.setCreatedDate(currentDateTime);
        bookReservation.setLastModified(currentDateTime);
        bookReservation.setReturnedDate(request.getReturnedDate());
        bookReservation.setStatus(BookReservationStatus.RESERVED);
        bookReservation.setChanged(0);
        bookReservation.setDeleted(false);
        bookReservation.setReservation(reservation);
        bookReservation.setBook(book);

        //call the save method
        Constants.BOOK_RESERVATION_MAPPER.toBookReservationDTO(repository
                .save(Constants.BOOK_RESERVATION_MAPPER.toBookReservationEntity(bookReservation)));

        /*
        request was dto
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
        Constants.BOOK_RESERVATION_MAPPER.toBookReservationDTO(repository.save(Constants.BOOK_RESERVATION_MAPPER.toBookReservationEntity(request)));

         */
    }

    @Transactional
    @Override
    public void update(Integer id, LocalDateTime newToReturnDateTime) {
        //change method parameters: reader id, book reservation id, new returned date

        //find book reservation by id and deleted false
        BookReservationDTO bookReservation = findByIdAndDeletedValueFalse(id);

        //validate the new returned date but now as current date you apply the last returned date
        //that the book reservation had, and you can push it up to 7 days
        ValidationUtils.checkIfValueIsNotCorrectlyValidated(newToReturnDateTime,
                bookReservation.getReturnedDate(),7);

        //maybe also check if the new date is the same as the last date

        //check if changed is not 3
        if (bookReservation.getChanged() == 3){
            throw new MethodCanNotBePerformedException("Book reservation can not be updated anymore.");
        }
        int newChangedValue = bookReservation.getChanged() + 1;
        bookReservation.setChanged(newChangedValue);

        LocalDateTime currentDateTime = LocalDateTime.now();

        //get reservation from dto
        // modify reservation last modified to current date and time
        // update
        ReservationDTO reservation = bookReservation.getReservation();
        reservation.setLastModified(currentDateTime);
        reservation = reservationService.update(reservation);

        // modify book reservation last modified to current date and time
        // modify book reservation returned date to new returned date and time
        // modify status to POSTPONED
        // modify the changed variable by incrementing it
        // set reservation to the new updated version
        bookReservation.setLastModified(currentDateTime);
        bookReservation.setReturnedDate(newToReturnDateTime);
        bookReservation.setStatus(BookReservationStatus.POSTPONED);
        bookReservation.setReservation(reservation);


        //update from repository
        Constants.BOOK_RESERVATION_MAPPER.toBookReservationDTO(repository
                .update(Constants.BOOK_RESERVATION_MAPPER.toBookReservationEntity(bookReservation)));

        /*
        request --> dto
        request.setLastModified(LocalDateTime.now());
        repository.save(Constants.BOOK_RESERVATION_MAPPER.toBookReservationEntity(request,
                repository.findById(request.getId())));*/
}

    @Transactional
    @Override
    public void update(BookReservationDTO bookReservation) {
        Constants.BOOK_RESERVATION_MAPPER.toBookReservationDTO(repository
                .update(Constants.BOOK_RESERVATION_MAPPER.toBookReservationEntity(bookReservation)));
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        //might just use soft delete for this since a book reservation should always be kept
        //check if status not returned and book reservation deleted = false
        // if yes throw exception, else soft delete
        BookReservationDTO bookReservation = findByIdAndDeletedValueFalse(id);
        if(bookReservation.getStatus() != BookReservationStatus.RETURNED){
            throw new MethodCanNotBePerformedException("Book reservation with id: " + id +
                    " can not be deleted, since the book has not been returned");
        }
        Constants.BOOK_RESERVATION_MAPPER.toBookReservationDTO(repository
                .softDelete(Constants.BOOK_RESERVATION_MAPPER.toBookReservationEntity(bookReservation)));

    }

    @Override
    public void softDelete(Integer id) {

    }

    @Transactional
    @Override
    public void updateStatusToReturned(Integer id) {
        BookReservationDTO bookReservation = findByIdAndDeletedValueFalse(id);
        if(bookReservation.getStatus() == BookReservationStatus.RETURNED){
            throw new MethodCanNotBePerformedException("Book reservation with id: " + id +
                    " has already been returned");
        }
        PhysicalCopyDTO book = bookReservation.getBook();
        int newNumberOfAvailableCopies = book.getNumberOfCopiesAvailable() + 1;
        book.setNumberOfCopiesAvailable(newNumberOfAvailableCopies);
        book = bookService.update(book);

        LocalDateTime currentDateTime = LocalDateTime.now();

        ReservationDTO reservation = bookReservation.getReservation();
        reservation.setLastModified(currentDateTime);
        reservation = reservationService.update(reservation);

        bookReservation.setLastModified(currentDateTime);
        bookReservation.setStatus(BookReservationStatus.RETURNED);
        bookReservation.setBook(book);
        bookReservation.setReservation(reservation);
        update(bookReservation);
    }

    @Override
    public List<BookReservationDTO> findBookReservationsByReservationId(Integer id) {
        reservationService.findByIdAndDeletedValueFalse(id);
        List<BookReservationEntity> bookReservations = repository.findBookReservationsByReservationId(id);
        if (bookReservations == null){
            throw new ResourceNotFoundException("There are no book reservations for reservation with id: "
                    + id);
        }
        return bookReservations.stream()
                .map(Constants.BOOK_RESERVATION_MAPPER::toBookReservationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookReservationDTO> findBookReservationsByReservationIdAndStatusNotReturned(Integer id) {
        reservationService.findByIdAndDeletedValueFalse(id);
        List<BookReservationEntity> bookReservations = repository
                .findBookReservationsByReservationIdAndStatusNotReturned(id);
        if (bookReservations == null){
            throw new ResourceNotFoundException("There are no book reservations for reservation with id: "
                    + id);
        }
        return bookReservations.stream()
                .map(Constants.BOOK_RESERVATION_MAPPER::toBookReservationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookReservationDTO> findBookReservationsByBookId(Integer id) {
        bookService.findById(id);
        List<BookReservationEntity> bookReservations = repository.findBookReservationsByBookId(id);
        if (bookReservations == null){
            throw new ResourceNotFoundException("There are no book reservations for book with id: "
                    + id);
        }
        return bookReservations.stream()
                .map(Constants.BOOK_RESERVATION_MAPPER::toBookReservationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookReservationDTO> findBookReservationsByIdAndLocalDate(Integer id, LocalDateTime createdDate) {
        repository.findByIdAndDeletedValueFalse(id);
        List<BookReservationEntity> bookReservations = repository.findReservationsByIdAndLocalDate(id,createdDate.toLocalDate());
        if (bookReservations == null){
            throw new ResourceNotFoundException("There are no book reservations with id: " + id +
                    " and created date: " + createdDate);
        }
        return bookReservations.stream()
                .map(Constants.BOOK_RESERVATION_MAPPER::toBookReservationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookReservationDTO> findBookReservationsByLocalDate(LocalDateTime createdDate) {
        List<BookReservationEntity> bookReservations = repository.findReservationsByLocalDate(createdDate.toLocalDate());
        if (bookReservations == null){
            throw new ResourceNotFoundException("There are no book reservations created on date: " + createdDate);
        }
        return bookReservations.stream()
                .map(Constants.BOOK_RESERVATION_MAPPER::toBookReservationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookReservationDTO> getAllBookReservations(Pageable pageable) {
        return null;
    }

}
