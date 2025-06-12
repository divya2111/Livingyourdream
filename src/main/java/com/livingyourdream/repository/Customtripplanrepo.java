package com.livingyourdream.repository;

import com.livingyourdream.model.Customtripplan;
import com.livingyourdream.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Customtripplanrepo extends JpaRepository<Customtripplan,Long> {
   // List<Customtripplan> findByUser(User user);
}
