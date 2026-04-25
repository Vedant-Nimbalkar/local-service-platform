package com.localservice.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.localservice.model.Booking;
import com.localservice.model.ServicePartner;
import com.localservice.model.ServicePartnerRequest;
import com.localservice.repository.BookingRepository;
import com.localservice.service.BookingService;
import com.localservice.service.ServicePartnerService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/partner")
public class ServicePartnerController {

    @Autowired
    private ServicePartnerService servicePartnerService;

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private BookingRepository bookingRepository;


    // Partner registration page
    @GetMapping("/register")
    public String registerForm(Model model){

        model.addAttribute("partner", new ServicePartnerRequest());

        return "partner-register";
    }

    // Waiting page
    @GetMapping("/waiting")
    public String waitingPage(){
        return "waiting";
    }

    // Submit partner registration
    @PostMapping("/register")
    public String registerPartner(@ModelAttribute ServicePartnerRequest request){

        servicePartnerService.submitRequest(request);

        return "redirect:/partner/waiting";
    }

    // Partner login page
    @GetMapping("/login")
    public String partnerLoginPage(){
        return "partner-login";
    }

    // Partner login
    @PostMapping("/login")
    public String partnerLogin(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               Model model){

        ServicePartner partner = servicePartnerService.login(email,password);

        if(partner == null){
            model.addAttribute("error","Invalid credentials");
            return "partner-login";
        }

        session.setAttribute("loggedPartner", partner);

        return "redirect:/partner/dashboard";
    }

    // Partner dashboard

    @GetMapping("/dashboard")
    public String partnerDashboard(HttpSession session, Model model){

        ServicePartner partner =
                (ServicePartner) session.getAttribute("loggedPartner");

        if(partner == null){
            return "redirect:/partner/login";
        }

        model.addAttribute(
                "bookings",
                bookingRepository.findByPartnerId(partner.getId())
        );

        return "partner-dashboard";
    } 
    
    @GetMapping("/booking/accept/{id}")
    public String acceptBooking(@PathVariable Long id){

        Booking booking = bookingRepository.findById(id).orElseThrow();

        booking.setStatus("CONFIRMED");

        bookingRepository.save(booking);

        return "redirect:/partner/dashboard";
    }

    @GetMapping("/booking/start/{id}")
    public String startWork(@PathVariable Long id){

        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus("IN_PROGRESS");

        bookingRepository.save(booking);

        return "redirect:/partner/dashboard";
    }


    @GetMapping("/booking/complete/{id}")
    public String completeWork(@PathVariable Long id){

        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus("COMPLETED");

        bookingRepository.save(booking);

        return "redirect:/partner/dashboard";
    }
    
    @GetMapping("/booking/reject/{id}")
    public String rejectBooking(@PathVariable Long id){

        Booking booking = bookingRepository.findById(id).orElseThrow();

        booking.setStatus("REJECTED");

        bookingRepository.save(booking);

        return "redirect:/partner/dashboard";
    }

}