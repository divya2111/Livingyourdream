package com.livingyourdream.controller;

import com.livingyourdream.model.Place;
import com.livingyourdream.service.Placeservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    private Placeservice placeservice;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Place> addplace(@RequestBody Place place){
        return ResponseEntity.ok(placeservice.addplace(place));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Place> updateplace(@PathVariable Long id,@RequestBody Place place){
        return ResponseEntity.ok(placeservice.updateplace(id, place));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Place> deleteplace(@PathVariable Long id){
        placeservice.deleteplace(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<Place>> getAllPlaces(){
        return ResponseEntity.ok(placeservice.getallplaces());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Place> getPlaceById(@PathVariable Long id){
        return placeservice.getplacebyid(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Place>> getPlacesByType(@PathVariable String type){
        return ResponseEntity.ok(placeservice.getplacebytype(type));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Place>> getPlacesByName(@PathVariable String name){
        return ResponseEntity.ok(placeservice.getplacebyname(name));
    }
    @GetMapping("/city/{city}")
    public ResponseEntity<List<Place>> getPlacesByCity(@PathVariable String city) {
        return ResponseEntity.ok(placeservice.getplacebycity(city));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<Place>> getPlacesByState(@PathVariable String state) {
        return ResponseEntity.ok(placeservice.getplacebystate(state));
    }
    @GetMapping("/country/{country}")
    public ResponseEntity<List<Place>> getPlacesByCountry(@PathVariable String country) {
        return ResponseEntity.ok(placeservice.getplacebycountry(country));
    }
    @GetMapping("/location/{city}/{state}/{country}")
    public ResponseEntity<List<Place>> getPlacesBylocation(@PathVariable String city,@PathVariable String state, @PathVariable String country) {
        return ResponseEntity.ok(placeservice.getplacebycitycountryandstate(city,state,country));
    }

}
