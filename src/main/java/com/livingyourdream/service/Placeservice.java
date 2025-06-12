package com.livingyourdream.service;

import com.livingyourdream.model.Place;
import com.livingyourdream.repository.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Placeservice {

    @Autowired
    private PlaceRepo placeRepo;

         public Place addplace(Place place){
           return placeRepo.save(place);

         }
         public Place updateplace(Long id , Place updatePlace){
             Place place = placeRepo.findById(id).orElseThrow();
             place.setName(updatePlace.getName());
             place.setDescription(updatePlace.getDescription());
             place.setType(updatePlace.getType());
             place.setCity(updatePlace.getCity());
             place.setCountry(updatePlace.getCountry());
             place.setPhotoUrl(updatePlace.getPhotoUrl());

             return placeRepo.save(place);

         }

         public void deleteplace(Long id){
             placeRepo.deleteById(id);
         }

         public List<Place> getallplaces(){
             return placeRepo.findAll();
         }

         public Optional<Place> getplacebyid(Long id){
             return placeRepo.findById(id);
         }

         public List<Place> getplacebytype(String type){
             return placeRepo.findByType(type);
         }

         public List<Place> getplacebyname(String name){
             return placeRepo.findByName(name);
         }

         public List<Place> getplacebycity(String city){ return placeRepo.findByCity(city); }

         public List<Place> getplacebycountry(String country){
             return placeRepo.findByCountry(country);
         }
         public List<Place> getplacebystate(String state){
             return placeRepo.findByState(state);
         }

         public List<Place> getplacebycitycountryandstate(String city,String state, String country){
             return placeRepo.findByCityAndStateAndCountry(city,state,country);
         }
}
