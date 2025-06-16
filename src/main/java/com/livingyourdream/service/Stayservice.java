package com.livingyourdream.service;

import com.livingyourdream.Enum.StayStatus;
import com.livingyourdream.Exception.ResourceNotFoundException;
import com.livingyourdream.dto.StayBookingDto;
import com.livingyourdream.model.*;
import com.livingyourdream.repository.Customtripplanrepo;
import com.livingyourdream.repository.Defaulttripplanrepo;
import com.livingyourdream.repository.StayBookingrepo;
import com.livingyourdream.repository.Stayrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Stayservice {

    @Autowired
    private Stayrepo stayrepo;

    @Autowired
    private Defaulttripplanrepo defaulttripplanrepo;

    @Autowired
    private Customtripplanrepo customtripplanrepo;

    @Autowired
    private StayBookingrepo stayBookingrepo;


    public Stay createstay(Stay stay){
        return stayrepo.save(stay);
    }

    public Stay updatestay(Long id, Stay updatedstay){
        Stay stayin = stayrepo.findById(id).orElseThrow();
        stayin.setName(updatedstay.getName());
        stayin.setCity(updatedstay.getCity());
        stayin.setState(updatedstay.getState());
        stayin.setCountry(updatedstay.getCountry());
        stayin.setType(updatedstay.getType());
        stayin.setContact(updatedstay.getContact());

        return stayrepo.save(stayin);
    }
   public void deletestay(Long id){
         stayrepo.deleteById(id);
    }
    public List<Stay> getAllstays(){
           return stayrepo.findAll();
    }

    public Optional<Stay> getstaybyid(Long id){
        return stayrepo.findById(id);
    }

    public List<Stay> getstaybytype(String type){
        return stayrepo.findByType(type);
    }

    public List<Stay> getstaybyname(String name){
        return stayrepo.findByName(name);
    }

    public List<Stay> getstaybycity(String city){ return stayrepo.findByCity(city); }

    public List<Stay> getstaybycountry(String country){
        return stayrepo.findByCountry(country);
    }
    public List<Stay> getstaybystate(String state){
        return stayrepo.findByState(state);
    }

    public List<Stay> getstaybycitycountryandstate(String city,String state, String country){
        return stayrepo.findByCityAndStateAndCountry(city,state,country);
    }



public List<StayBooking> addstays(Long stayId, List<StayBookingDto> stays) {
    Stay stay = stayrepo.findById(stayId)
            .orElseThrow(() -> new ResourceNotFoundException("stay not found"));

    List<StayBooking> bookings = new ArrayList<>();

    for (StayBookingDto dto : stays) {
        StayBooking sb = new StayBooking();
        sb.setStay(stay);
        sb.setRoom_no(dto.getRoom_no());
        sb.setCheckInDate(dto.getDate());

        bookings.add(sb);
    }

    return stayBookingrepo.saveAll(bookings);
}


}
