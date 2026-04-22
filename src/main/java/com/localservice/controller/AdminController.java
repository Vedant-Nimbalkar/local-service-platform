package com.localservice.controller;

import com.localservice.repository.ServicePartnerRequestRepository;
import com.localservice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ServicePartnerRequestRepository requestRepo;

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(Model model){

        model.addAttribute("requests", requestRepo.findAll());
        return "admin-dashboard";
    }

    @GetMapping("/approve/{id}")
    public String approvePartner(@PathVariable Long id){

        adminService.approvePartner(id);

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/reject/{id}")
    public String reject(@PathVariable Long id){

        adminService.rejectPartner(id);

        return "redirect:/admin/dashboard";
    }
}