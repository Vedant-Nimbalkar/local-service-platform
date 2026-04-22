package com.localservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.localservice.model.Booking;
import com.localservice.model.ServicePartner;
import com.localservice.model.User;
import com.localservice.repository.BookingRepository;
import com.localservice.repository.ServicePartnerRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private ServicePartnerRepository servicePartnerRepository;

    @PostMapping("/booking/create")
    public String createBooking(@ModelAttribute Booking booking, HttpSession session){

        User user = (User) session.getAttribute("loggedUser");

        booking.setUserEmail(user.getEmail());
        booking.setCustomerName(user.getName());
        booking.setStatus("PENDING");

        // fetch partner to get service type
        ServicePartner partner = servicePartnerRepository
                .findById(booking.getPartnerId())
                .orElseThrow();

        booking.setServiceType(partner.getServiceType());

        bookingRepository.save(booking);

        return "redirect:/services";
    }
}