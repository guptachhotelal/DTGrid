package com.dt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {

	@GetMapping("login")
	public String login(Model model, String error, String logout) {
		if (error != null) {
			model.addAttribute("msg", "Your username or password are invalid.");
		}

		if (logout != null) {
			model.addAttribute("msg", "You have been logged out successfully.");
		}
		return "common/login";
	}

	@GetMapping({ "/", "home" })
	public String home(Model model) {
		log.info("This is home");
		return "jsp/home";
	}
}
