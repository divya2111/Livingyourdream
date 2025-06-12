package com.livingyourdream.service;

import com.livingyourdream.model.Booking;
import com.livingyourdream.model.Checklist;
import com.livingyourdream.model.ChecklistItem;
import com.livingyourdream.model.Place;
import com.livingyourdream.repository.Bookingrepo;
import com.livingyourdream.repository.ChecklistItemrepo;
import com.livingyourdream.repository.Checklistrepo;
import com.livingyourdream.repository.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Checklistservice {

    @Autowired
    private Checklistrepo checklistrepo;

    @Autowired
    private ChecklistItemrepo checklistItemrepo;

    @Autowired
    private Bookingrepo bookingrepo;

    @Autowired
    private PlaceRepo placeRepo;

    public Checklist createChecklist(Long bookingId){
        Booking booking = bookingrepo.findById(bookingId)
                .orElseThrow(() ->new RuntimeException("Booking not found"));

        Checklist checklist = new Checklist();
        checklist.setBooking(booking);
        checklist.setStartDate(booking.getStartDate());
        checklist.setEndDate(booking.getEndDate());
         checklistrepo.save(checklist);

        List<String> basicItems = List.of("ID proof","Charger","Toiletries","Medicines","Money");


        Set<String> placeTypes = new HashSet<>();
        if (booking.getDefaultTripPlans() != null) {
            booking.getDefaultTripPlans().getPlaces().forEach(place -> placeTypes.add(place.getType()));
        } else if (booking.getCustomTripPlans() != null) {
            booking.getCustomTripPlans().getPlaces().forEach(place -> placeTypes.add(place.getType()));
        }
        List<String> categoryItems = new ArrayList<>();
        for (String type : placeTypes) {
            if ("Spiritual".equalsIgnoreCase(type)) {
                categoryItems = List.of("Traditional Wear", "Prayer Items", "Water Bottle", "Temple tickets/passes");
            } else if ("Adventure".equalsIgnoreCase(type)) {
                categoryItems = List.of("Trekking shoes", "First adi kit", "Energy bars", "Torchligth", "Water Bottle");
            } else if ("Beach".equalsIgnoreCase(type)) {
                categoryItems = List.of("Suncreen", "Beachwear", "Towel");
            }
        }

        // Collect transport mode
        String transportMode = "";
        if (booking.getDefaultTripPlans() != null && booking.getDefaultTripPlans().getTransports() != null) {
            transportMode = booking.getDefaultTripPlans().getTransports().get(0).getMode().toString();
        } else if (booking.getCustomTripPlans() != null && booking.getCustomTripPlans().getTransports() != null) {
            transportMode = booking.getCustomTripPlans().getTransports().get(0).getMode().toString();
        }

        List<String> transportItems = new ArrayList<>();
        if ("Flight".equalsIgnoreCase(transportMode)) {
            transportItems = List.of("Flight tickets", "Passport/ID", "Boarding pass");
        } else if ("Train".equalsIgnoreCase(transportMode)) {
            transportItems = List.of("Train tickets", "Snacks", "Blanket");
        } else if ("Bus".equalsIgnoreCase(transportMode)) {
            transportItems = List.of("Bus tickets", "Neck pillow", "Snacks");
        }

        List<ChecklistItem> items = new ArrayList<>();

        basicItems.forEach(item -> {
            ChecklistItem ci = new ChecklistItem();
            ci.setItem(item);
            ci.setCompleted(false);
            ci.setDueDate(booking.getStartDate().minusDays(2));
            ci.setChecklist(checklist);
            items.add(ci);
        });

        categoryItems.forEach(item -> {
            ChecklistItem ci = new ChecklistItem();
            ci.setItem(item);
            ci.setCompleted(false);
            ci.setDueDate(booking.getStartDate().minusDays(2));
            ci.setChecklist(checklist);
            items.add(ci);
        });

        transportItems.forEach(item -> {
            ChecklistItem ci = new ChecklistItem();
            ci.setItem(item);
            ci.setCompleted(false);
            ci.setDueDate(booking.getStartDate().minusDays(2));
            ci.setChecklist(checklist);
            items.add(ci);
        });

        checklistItemrepo.saveAll(items);
        checklist.setItems(items);

        return checklist;


    }

    public List<ChecklistItem> getChecklistItems(Long bookingId) {
        Checklist checklist = checklistrepo.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Checklist not found for booking"));
        return checklistItemrepo.findByChecklist_ChecklistId(checklist.getChecklistId());
    }

    public ChecklistItem updateItemStatus(Long itemId, boolean completed)  {
        ChecklistItem item = checklistItemrepo.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item not found"));
        item.setCompleted(completed);
        return  checklistItemrepo.save(item);
    }
}
