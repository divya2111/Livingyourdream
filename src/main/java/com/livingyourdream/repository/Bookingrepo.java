package com.livingyourdream.repository;

import com.livingyourdream.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Bookingrepo extends JpaRepository<Booking,Long> {
//    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId")
//    List<Booking> findBookingsByUserId(@Param("userId") Long userId);

}
