package com.livingyourdream.repository;

import com.livingyourdream.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface Userrepo extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
