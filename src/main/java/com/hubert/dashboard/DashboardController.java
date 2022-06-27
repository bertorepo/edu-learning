package com.hubert.dashboard;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping(value = {"/", "/home", "/dashboard" })
	public String displayDashboardPage(Authentication auth, Model model) {
		model.addAttribute("username", auth.getName());
		model.addAttribute("role", auth.getAuthorities().toString());

		return "/dashboard";
	}

//	@GetMapping("/")
//	public String displayDefault() {
//		return "home";
//	}
}
