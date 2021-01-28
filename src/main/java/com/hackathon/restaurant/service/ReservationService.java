package com.hackathon.restaurant.service;

import com.hackathon.restaurant.model.Reservation;
import com.hackathon.restaurant.repository.ReservationRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ReservationService {

    private final MongoTemplate mongoTemplate;
    @Autowired
    ReservationRepository reservationRepository;

    public ReservationService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ResponseEntity<List<Reservation>> getAllReservations(String name, int page, int limit) {
        try {
            Query query = new Query();
            if (name != null) {
                query.addCriteria(Criteria.where("name").is(name));
            }
            query.skip(page).limit(limit);
            List<Reservation> reservations = mongoTemplate.find(query, Reservation.class);
            if (reservations.isEmpty()) {
                log.info("No reservations found");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (Exception e) {
            log.error("unable to fetch reservations");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Reservation> getReservationById(String reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent())
            return new ResponseEntity<>(reservation.get(), HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Reservation> createReservation(Reservation reservation) {
        try {
            Reservation _reservation = reservationRepository.save(reservation);
            return new ResponseEntity<>(_reservation, HttpStatus.OK);
        } catch (Exception e) {
            log.error("create reservation failed");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Reservation> updateReservation(String reservationId, Reservation reservation) {
        Optional<Reservation> reservationData = reservationRepository.findById(reservationId);
        try {
            if (reservationData.isPresent()) {
                Reservation _reservation = reservationData.get();
                _reservation.setName(reservation.getName());
                _reservation.setSize(reservation.getSize());
                _reservation.setPhone(reservation.getPhone());
                _reservation.setStartDateAndTime(reservation.getStartDateAndTime());
                _reservation.setEndDateAndTime(reservation.getEndDateAndTime());
                return new ResponseEntity<>(reservationRepository.save(_reservation), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("update reservation failed, {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteReservation(String reservationId) {
        try {
            reservationRepository.deleteById(reservationId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteAllReservations() {
        try {
            reservationRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
