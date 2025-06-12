package com.livingyourdream.repository;

import com.livingyourdream.model.Defaulttripplan;
import com.livingyourdream.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Defaulttripplanrepo extends JpaRepository<Defaulttripplan,Long> {
//    @Query("SELECT d FROM DefaultTripPlan d JOIN d.places p WHERE p = :place")
//    List<Defaulttripplan> findByPlace(@Param("place") Place place);
    List<Defaulttripplan> findByPlaces(Place place);
}
