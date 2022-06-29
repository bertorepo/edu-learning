package com.hubert.customer;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user")
public class CustomerController {

	@GetMapping("/addUser")
	public String displayAddCustomerPage(Model model) {
		model.addAttribute("customerDao", new CustomerDao());
		return "register";
	}

	@PostMapping("/createUser")
	public String addCustomer(@Valid @ModelAttribute("customerDao") CustomerDao customerDao, Errors errors, Model model) {
		if (errors.hasErrors()) {
			return "register";
		}
		System.out.println(customerDao.toString());
		return "redirect:/login?register=true";
	}

}
