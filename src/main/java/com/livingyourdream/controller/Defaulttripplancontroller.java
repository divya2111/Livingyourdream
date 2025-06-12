package com.livingyourdream.controller;

import com.livingyourdream.model.Defaulttripplan;
import com.livingyourdream.model.Place;
import com.livingyourdream.service.Defaulttripplanservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/defaultplans")
public class Defaulttripplancontroller {

    @Autowired
    private Defaulttripplanservice defaulttripplanservice;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Defaulttripplan> Createplans(@RequestBody Defaulttripplan plan){
        return  ResponseEntity.ok(defaulttripplanservice.saveplan(plan));

    }

    @GetMapping
    public ResponseEntity<List<Defaulttripplan>> findallPlans() {
        return ResponseEntity.ok(defaulttripplanservice.findallplans());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Defaulttripplan> getbyid(@PathVariable Long id){
        return defaulttripplanservice.findbyid(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Defaulttripplan> deleteplan(@PathVariable Long id){
        defaulttripplanservice.deleteplan(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Defaulttripplan> updateplan(@PathVariable Long id, @RequestBody Defaulttripplan defaulttripplan){
        return ResponseEntity.ok(defaulttripplanservice.updateplan(id, defaulttripplan));
    }

    @GetMapping("/place/{place}")
    public ResponseEntity<List<Defaulttripplan>> getbyplace(@PathVariable Place place){
        return  ResponseEntity.ok(defaulttripplanservice.findbyplace(place));
    }

}
