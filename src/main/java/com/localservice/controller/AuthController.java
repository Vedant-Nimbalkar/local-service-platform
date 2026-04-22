package com.localservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.localservice.model.User;
import com.localservice.security.JwtUtil;
import com.localservice.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	//@PostMapping("/api/login")
	@ResponseBody
	public String login(@RequestParam String email,
	                    @RequestParam String password){

	User user = userService.login(email,password);

	   if (user == null) {
	        return "Invalid credentials";
	    }

	    return jwtUtil.generateToken(user.getEmail());
	}
	

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session) {

		User user = (User) session.getAttribute("loggedUser");

		if (user == null) {
			return "redirect:/login";
		}

		return "user-dashboard";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		session.invalidate();

		return "redirect:/login";
	}
}