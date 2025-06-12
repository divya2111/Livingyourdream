package com.livingyourdream.repository;

import com.livingyourdream.model.Booking;
import com.livingyourdream.model.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Checklistrepo extends JpaRepository<Checklist,Long> {
    @Query("SELECT c FROM Checklist c WHERE c.booking.id = :bookingId")
    Optional<Checklist> findByBookingId(@Param("bookingId") Long bookingId);

}
