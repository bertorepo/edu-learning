package com.hubert.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hubert.constants.MessageResponse;

@Controller
@RequestMapping("/admin/user")
public class CustomerController {

	private ICustomerService customerSevice;

	@Autowired
	public CustomerController(ICustomerService customerSevice) {
		this.customerSevice = customerSevice;
	}

	@GetMapping("/addUser")
	public String displayAddCustomerPage(Model model, @RequestParam(value = "error", required = false) String error) {
		MessageResponse messageResponse = new MessageResponse();
		List<String> generateMessage = null;
		if (error != null) {
			generateMessage = MessageResponse.generateMessage("There's a problem adding the customer", error);
			model.addAttribute(messageResponse.getMessage(), generateMessage.get(0));
			model.addAttribute(messageResponse.getMessageType(), generateMessage.get(1));
		}

		model.addAttribute("customerDao", new CustomerDao());
		return "register";
	}

	@PostMapping("/createUser")
	public String addCustomer(@Valid @ModelAttribute("customerDao") CustomerDao customerDao, Errors errors, Model model,
			HttpServletRequest request, HttpServletResponse reponse) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (errors.hasErrors()) {
			return "register";
		}
		boolean isSaved = customerSevice.saveCustomer(customerDao);
		if (!isSaved) {
			return "redirect://admin/user/addUser?error=true";
		}

		// admin will auto logout after adding new customer
		// you can disable this if your adding multiple customer to make it less hassle
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, reponse, auth);
		}

		return "redirect:/login?register=true";
	}

}
