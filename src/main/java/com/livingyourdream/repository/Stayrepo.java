package com.livingyourdream.repository;

import com.livingyourdream.model.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface Stayrepo extends JpaRepository<Stay,Long> {
    List<Stay> findByType(String type);

    List<Stay> findByName(String name);

    List<Stay> findByCity(String city);

    List<Stay> findByCountry(String country);

    List<Stay> findByState(String state);

    List<Stay> findByCityAndStateAndCountry(String city, String state, String country);
}
