package com.livingyourdream.repository;

import com.livingyourdream.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepo extends JpaRepository<Place,Long> {

    List<Place> findByType(String type);
    List<Place> findByName(String name);

    List<Place> findByCity(String city);

    List<Place> findByCountry(String country);
    List<Place> findByState(String state);

    List<Place> findByCityAndStateAndCountry(String city,String state, String country);
}
