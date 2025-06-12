package com.livingyourdream.service;

import com.livingyourdream.model.Role;
import com.livingyourdream.model.User;
import com.livingyourdream.repository.Rolerepo;
import com.livingyourdream.repository.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class Userservice {

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private Rolerepo rolerepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> userRole = rolerepo.findByName("ROLE_USER");
        if(userRole.isEmpty()){
                throw new RuntimeException("ROLE_USER not found");
        }
        Role userRoles = userRole.get(0);
        user.setRoles(Set.of(userRoles));
        return userrepo.save(user);
    }
    public User registerAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> adminRoles = rolerepo.findByName("ROLE_ADMIN");
        if (adminRoles.isEmpty()) {
            throw new RuntimeException("Admin role not found");
        }
        Role adminRole = adminRoles.get(0); // or handle based on some logic
        user.setRoles(Set.of(adminRole));
        return userrepo.save(user);
    }
    public List<User> getAllUsers() {
            return userrepo.findAll();
        }


    public Optional<User> getuserbyid(Long user_id){
        return userrepo.findById(user_id);
    }
    public void DeleteById(Long user_id){
        userrepo.deleteById(user_id);

    }
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userrepo.findByUsername(username).orElse(null));
    }
}
