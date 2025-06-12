package com.livingyourdream.controller;

import com.livingyourdream.model.Customtripplan;
import com.livingyourdream.model.User;
import com.livingyourdream.repository.Userrepo;
import com.livingyourdream.service.Customtripplanservice;
import com.livingyourdream.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customplan")
public class Customtripplancontroller {

    @Autowired
    private Customtripplanservice customtripplanservice;

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private Userservice userservice;


    @PostMapping
    public Customtripplan createPlan(@RequestBody Customtripplan plan, Authentication authentication) {
        String username = authentication.getName();

        Optional<User> useropt = userservice.findByUsername(username);
        if(useropt.isEmpty()){
            throw new RuntimeException("User not found for username: "+username);
        }
        Long userId = useropt.get().getUser_id();
        return customtripplanservice.savePlan(plan,userId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Customtripplan> getAllCustomPlans() {
        return customtripplanservice.getAllPlans();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customtripplan> updatecustomplan(@PathVariable Long id, @RequestBody Customtripplan customtripplan){
        return ResponseEntity.ok(customtripplanservice.updatecustomplan(id, customtripplan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customtripplan> deletecustomplan(@PathVariable Long id){
         customtripplanservice.deletecustomplan(id);
         return ResponseEntity.noContent().build();

    }
}
