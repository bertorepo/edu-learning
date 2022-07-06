package com.hubert.dashboard;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hubert.customer.Customer;
import com.hubert.customer.ICustomerService;

@Controller
public class DashboardController {

	private ICustomerService customerService;

	@Autowired
	public DashboardController(ICustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping(value = { "/", "/home", "/dashboard" })
	public String displayDashboardPage(Authentication auth, HttpSession session, Model model) {

		Customer loggedInCustomer = customerService.getCustomerByUsername(auth.getName());

		model.addAttribute("username", auth.getName());
		model.addAttribute("role", loggedInCustomer.getAuthorities().toString());

		// session helps us getting the loggedin customer and populate all the
		// information accross pages
		session.setAttribute("loggedInCustomer", loggedInCustomer);
		return "/dashboard";
	}

	// @GetMapping("/")
	// public String displayDefault() {
	// return "home";
	// }
}
