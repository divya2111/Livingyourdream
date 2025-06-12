package com.livingyourdream.repository;

import com.livingyourdream.model.StayBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StayBookingrepo extends JpaRepository<StayBooking,Long> {


}
