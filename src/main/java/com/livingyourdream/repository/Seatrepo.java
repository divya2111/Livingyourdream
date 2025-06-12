package com.livingyourdream.repository;

import com.livingyourdream.Enum.SeatStatus.SeatStatus;
import com.livingyourdream.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Seatrepo extends JpaRepository<Seat,Long> {
    @Query("SELECT s FROM Seat s WHERE s.transport.vehicleNo = :vehicleNo")
    List<Seat> findByVehicleNo(@Param("vehicleNo") String vehicleNo);
}
