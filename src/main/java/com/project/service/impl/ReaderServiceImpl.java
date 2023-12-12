package com.project.service.impl;

import com.project.domain.dto.ReaderDTO;
import com.project.domain.dto.ReaderRequest;
import com.project.domain.entity.ReaderEntity;
import com.project.domain.mapper.ReaderMapper;
import com.project.filter.Filter;
import com.project.repository.BookReservationRepository;
import com.project.repository.ReaderRepository;
import com.project.repository.ReservationRepository;
import com.project.service.ReaderService;
import com.project.util.ValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReaderServiceImpl implements ReaderService {

    //@Autowired
    private final ReaderRepository repository;

    //@Autowired
    private final ReservationRepository reservationRepository;

    //@Autowired
    private final BookReservationRepository bookReservationRepository;

    @Autowired
    public ReaderServiceImpl(ReaderRepository repository, ReservationRepository reservationRepository,
                             BookReservationRepository bookReservationRepository) {
        this.repository = repository;
        this.reservationRepository = reservationRepository;
        this.bookReservationRepository = bookReservationRepository;
    }

    //admin only
    @Override
    public ReaderDTO findById(Integer id) {
        return ReaderMapper.toDTO(repository.findById(id));
    }

    //user and admin
    @Override
    public ReaderDTO findByIdAndDeletedValueFalse(Integer id) {
        return ReaderMapper.toDTO(repository.findByIdAndDeletedValueFalse(id));
    }

    //will probably delete
    @Override
    public ReaderDTO findByIdAndDeletedValueTrue(Integer id) {
        return ReaderMapper.toDTO(repository.findByIdAndDeletedValueTrue(id));
    }

    //user (?) and admin
    @Transactional
    @Override
    public ReaderDTO save(ReaderRequest request) {

        request.setName(ValidationUtils.checkIfValueIsNotCorrectlyValidated(request.getName(), "name"));
        request.setSurname(ValidationUtils.checkIfValueIsNotCorrectlyValidated(request.getSurname(), "surname"));
        request.setEmail(ValidationUtils.checkIfValueIsNotCorrectlyValidated(request.getEmail(), "email"));
        request.setPhoneNumber(ValidationUtils.checkIfValueIsNotCorrectlyValidated(request.getPhoneNumber(), "phoneNumber"));

        ValidationUtils.checkIfReaderWithGivenEmailAlreadyExists(request,repository);

        ReaderEntity reader = ReaderMapper.toEntity(request);
        reader.setDeleted(false);

        return ReaderMapper.toDTO(repository.save(reader));
    }

    //user only
    @Transactional
    @Override
    public ReaderDTO update(Integer id, ReaderRequest request) {

        findByIdAndDeletedValueFalse(id);

        request.setName(ValidationUtils.checkIfValueIsNotCorrectlyValidated(request.getName(), "name"));
        request.setSurname(ValidationUtils.checkIfValueIsNotCorrectlyValidated(request.getSurname(), "surname"));
        request.setEmail(ValidationUtils.checkIfValueIsNotCorrectlyValidated(request.getEmail(), "email"));
        request.setPhoneNumber(ValidationUtils.checkIfValueIsNotCorrectlyValidated(request.getPhoneNumber(), "phoneNumber"));

        ValidationUtils.checkIfReaderWithGivenEmailAlreadyExists(id, request, repository);

        ReaderDTO reader = ReaderMapper.toDTO(ReaderMapper.toEntity(request));
        reader.setId(id);

        return ReaderMapper.toDTO(repository.update(ReaderMapper.toEntity(reader)));
    }

    //per userin te behet soft delete, per adminin delete tamam
    /*@Transactional
    @Override
    public ReaderDTO delete(Integer id) {
        findById(id);
        if (ValidationUtils.checkBeforeDeleteToSoftDeleteIfConditionsAreMet(id,
                reservationRepository,bookReservationRepository)){
            return ReaderMapper.toDTO(repository.softDelete(repository.findById(id)));
        } else {
            return ReaderMapper.toDTO(repository.delete(repository.findById(id)));
        }
    }
    @Transactional
    @Override
    public ReaderDTO delete(Integer id) {
        findByIdAndDeletedValueFalse(id);
        if (ValidationUtils.checkBeforeDeleteToSoftDeleteIfConditionsAreMet(id,
                reservationRepository,bookReservationRepository)){
            return ReaderMapper.toDTO(repository.softDelete(repository.findById(id)));
        } else {
            return ReaderMapper.toDTO(repository.delete(repository.findById(id)));
        }
    }
    */

    //admin use only
    @Transactional
    @Override
    public ReaderDTO delete(Integer id) {
        findById(id);
        return ReaderMapper.toDTO(repository.delete(repository.findById(id)));
    }

    //kujdes findById njera duhet te jete me deleted = false , disa me deleted = true
    // dhe disa pa ja bere kushtin

    //user and admin use
    //add to the controller
    @Transactional
    @Override
    public ReaderDTO softDelete(Integer id) {
        findByIdAndDeletedValueFalse(id);
        ValidationUtils.checkBeforeDeleteToSoftDeleteIfConditionsAreMet(id,reservationRepository,bookReservationRepository);
        return ReaderMapper.toDTO(repository.softDelete(repository.findById(id)));
    }

    //will probably delete
    @Transactional
    @Override
    public ReaderDTO restore(Integer id) {
        findByIdAndDeletedValueTrue(id);
        ValidationUtils.performRestoreIfTheConditionsAreMet(id,reservationRepository,bookReservationRepository);
        return ReaderMapper.toDTO(repository.restore(repository.findById(id)));
    }

    @Override
    public List<ReaderDTO> getAllReaders(Filter... filters) {
        List<ReaderEntity> readers = repository.getAll(filters);
        return readers.stream()
                .map(ReaderMapper::toDTO)
                .collect(Collectors.toList());
    }

}
