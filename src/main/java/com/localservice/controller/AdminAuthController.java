package com.localservice.controller;

import com.localservice.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminAuthController {

    @Autowired
    private AdminRepository adminRepo;

    @GetMapping("/login")
    public String loginPage(){

        return "admin-login";

    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model){

        var admin = adminRepo.findByUsername(username);

        if(admin != null && admin.getPassword().equals(password)){

            return "redirect:/admin/dashboard";

        }

        model.addAttribute("error","Invalid credentials");

        return "admin-login";
    }
}