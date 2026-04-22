package com.localservice.controller;

import com.localservice.model.Booking;
import com.localservice.model.User;
import com.localservice.repository.BookingRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserBookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/my-bookings")
    public String myBookings(HttpSession session, Model model){

        User user = (User) session.getAttribute("loggedUser");

        if(user == null){
            return "redirect:/login";
        }

        model.addAttribute(
            "bookings",
            bookingRepository.findByUserEmail(user.getEmail())
        );

        return "my-bookings";
    }

}