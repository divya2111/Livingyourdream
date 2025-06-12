package com.livingyourdream.service;

import com.livingyourdream.Enum.BookingStatus.BookingStatus;
import com.livingyourdream.Enum.SeatStatus.SeatStatus;
import com.livingyourdream.Enum.StayStatus;
import com.livingyourdream.dto.BookingRequestDTO;
import com.livingyourdream.model.*;
import com.livingyourdream.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class Bookingservice {

    @Autowired
    private Bookingrepo bookingrepo;

    @Autowired
    private Defaulttripplanrepo defaulttripplanrepo;

    @Autowired
    private Customtripplanrepo customtripplanrepo;

    @Autowired
    private Transportrepo transportrepo;

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private Seatrepo seatrepo;

    @Autowired
    private StayBookingrepo stayBookingrepo;

    @Transactional
    public Booking createBooking(BookingRequestDTO dto, Authentication authentication) {
       Booking booking = new Booking();
//
//        booking.setBooking_date(LocalDate.now());
//        booking.setStatus(dto.getStatus());
//        booking.setStartDate(plan.getStartDate());
//        booking.setEndDate(plan.getEndDate());
//

        String username = authentication.getName();
        User user = userrepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        booking.setUser(user);

        if (dto.getPlan_id() != null && dto.getCustomplan_id() != null) {
            throw new IllegalArgumentException("Booking can only have either a default trip plan or a custom trip plan, not both.");
        }

        if (dto.getPlan_id() != null) {
            Defaulttripplan plan = defaulttripplanrepo.findById(dto.getPlan_id())
                    .orElseThrow(() -> new RuntimeException("DefaultTripPlan not found"));

            // Update seats
            if (plan.getSeats() != null) {
                for (Seat seat : plan.getSeats()) {
                    seat.setStatus(SeatStatus.BOOKED);
                    seatrepo.save(seat);
                }
            }

            // Update stay bookings
            if (plan.getStays() != null) {
                for (StayBooking sb : plan.getStays()) {
                    sb.setStatus(StayStatus.BOOKED);
                    stayBookingrepo.save(sb);
                }
            }



            booking.setBooking_date(LocalDate.now());
            booking.setStatus(dto.getStatus());
            booking.setStartDate(plan.getCheckInDate());
            booking.setEndDate(plan.getCheckOutDate());
            booking.setNumOfPeople(plan.getPeopleCount());
            booking.setTotal_cost(plan.getTotalCost());
            booking.setDefaultTripPlans(plan);
//            totalCost = plan.getTotalCost();
//            booking.setTotal_cost(totalCost);
           // booking.setStatus(BookingStatus.CONFIRMED);

        } else if (dto.getCustomplan_id() != null) {
            Customtripplan plan = customtripplanrepo.findById(dto.getCustomplan_id())
                    .orElseThrow(() -> new RuntimeException("CustomTripPlan not found"));

            // Update seats
            if (plan.getSeats() != null) {
                for (Seat seat : plan.getSeats()) {
                    seat.setStatus(SeatStatus.BOOKED);
                    seatrepo.save(seat);
                }
            }

            // Update stay bookings
            if (plan.getStays() != null) {
                for (StayBooking sb : plan.getStays()) {
                    sb.setStatus(StayStatus.BOOKED);
                    stayBookingrepo.save(sb);
                }
            }

            booking.setBooking_date(LocalDate.now());
            booking.setStatus(dto.getStatus());
            booking.setStartDate(plan.getCheckInDate());
            booking.setEndDate(plan.getCheckOutDate());
            booking.setNumOfPeople(plan.getPeopleCount());
            booking.setTotal_cost(plan.getTotalCost());

           booking.setCustomTripPlans(plan);
//            totalCost = plan.getTotalCost();
//            booking.setTotal_cost(totalCost);


        } else {
            throw new IllegalArgumentException("Booking must have either a default trip plan or a custom trip plan.");
        }

        return bookingrepo.save(booking);
    }


        public List<Booking> getAllBookings() {
        return bookingrepo.findAll();
    }
    public void deleteBooking(Long id) {
        bookingrepo.deleteById(id);
    }

    @Transactional
    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingrepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Update booking status
        booking.setStatus(BookingStatus.CANCELLED);

        // Release seats
        if (booking.getDefaultTripPlans() != null) {
            Defaulttripplan plan = booking.getDefaultTripPlans();
            if (plan.getSeats() != null) {
                for (Seat seat : plan.getSeats()) {
                    seat.setStatus(SeatStatus.AVAILABLE);
                    seatrepo.save(seat);
                }
            }
            if (plan.getStays() != null) {
                for (StayBooking sb : plan.getStays()) {
                    sb.setStatus(StayStatus.AVAILABLE);
                    stayBookingrepo.save(sb);
                }
            }
        } else if (booking.getCustomTripPlans() != null) {
            Customtripplan plan = booking.getCustomTripPlans();
            if (plan.getSeats() != null) {
                for (Seat seat : plan.getSeats()) {
                    seat.setStatus(SeatStatus.AVAILABLE);
                    seatrepo.save(seat);
                }
            }
            if (plan.getStays() != null) {
                for (StayBooking sb : plan.getStays()) {
                    sb.setStatus(StayStatus.AVAILABLE);
                    stayBookingrepo.save(sb);
                }
            }
        }

        return bookingrepo.save(booking); // Save the updated booking
    }
}
