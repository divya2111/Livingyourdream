package com.livingyourdream.repository;

import com.livingyourdream.Enum.Mode;
import com.livingyourdream.Enum.SeatStatus.SeatStatus;
import com.livingyourdream.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Transportrepo extends JpaRepository<Transport,Long> {
    List<Transport> findByDeparturelocationAndArrivallocation(String departureLocation, String arrivalLocation);

    Optional<Transport> findByVehicleNo(String vehicleNo);

    List<Transport> findByMode(Mode mode);
}
