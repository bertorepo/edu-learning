package com.hubert.customer;

import java.lang.ProcessBuilder.Redirect;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		model.addAttribute("updatableCustomer", null);

		return "register";
	}

	@PostMapping("/createUser")
	public String addCustomer(@Valid @ModelAttribute("customerDao") CustomerDao customerDao,
			BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse reponse) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (bindingResult.hasErrors()) {
			return "register";
		}
		boolean isSaved = customerSevice.saveCustomer(customerDao);
		if (!isSaved) {
			return "redirect:/admin/user/addUser?error=true";
		}

		// admin will auto logout after adding new customer
		// you can disable this if your adding multiple customer to make it less hassle
		// if (auth != null) {
		// new SecurityContextLogoutHandler().logout(request, reponse, auth);
		// }

		return "redirect:/admin/user/allUsers?register=true";
	}

	// display all customers

	@GetMapping("/allUsers")
	public String displayAllCustomersPage(Model model) {
		List<Customer> customerList = customerSevice.getAllCustomers();
		model.addAttribute("customerList", customerList);
		return "pages/customer/all-customers";
	}

	@RequestMapping(value = "/status/{customerId}", method = { RequestMethod.GET, RequestMethod.POST })
	public String setStatus(@PathVariable("customerId") Long customerId, Model model,
			RedirectAttributes redirectAttributes) {

		if (customerId == 0 || customerId == null) {
			redirectAttributes.addFlashAttribute("statusError", "Customer was not found");
			return "redirect:/admin/user/allUsers";
		}
		// call service
		if (customerSevice.setCustomerStatus(customerId)) {
			redirectAttributes.addFlashAttribute("statusSuccess", "Customer status was updated!");
			return "redirect:/admin/user/allUsers";
		}
		return "pages/customer/all-customers?statusUpdate=false";
	}

	@RequestMapping(value = "/delete/{deleteId}", method = { RequestMethod.GET, RequestMethod.DELETE })
	public String deleteCustomer(@PathVariable("deleteId") Long deleteId, Model model,
			RedirectAttributes redirectAttributes) {

		if (deleteId == 0 || deleteId == null) {
			redirectAttributes.addFlashAttribute("deleteError", "Customer was not found");
			return "redirect:/admin/user/allUsers";
		}
		// call service
		if (customerSevice.deleteCustomer(deleteId)) {
			redirectAttributes.addFlashAttribute("deleteSuccess", "Customer was deleted!");
			return "redirect:/admin/user/allUsers";
		}
		return "pages/customer/all-customers";
	}

	// update customer
	@GetMapping("/update/{customerId}")
	public String displayUpdateForm(Model model, @PathVariable("customerId") Long customerId) {

		model.addAttribute("updatableCustomer", customerId);
		CustomerDao customerDao = null;

		// get the Customer By id
		Customer existingCustomer = customerSevice.findCustomerById(customerId);

		// pass it to CustomerDao
		if (existingCustomer.getId() > 0) {
			customerDao = new CustomerDao();
			customerDao.setUsername(existingCustomer.getUsername());
			customerDao.setEmail(existingCustomer.getEmail());
			// faking he password
			customerDao.setPassword("nomercy");
		}

		model.addAttribute("customerDao", customerDao);

		return "register";
	}

	@PostMapping("/update/{customerId}")
	public String updateCustomer(@Valid @ModelAttribute("customerDao") CustomerDao customerDao,
			BindingResult bindingResult, Model model, @PathVariable("customerId") Long customerId,
			RedirectAttributes redirect) {

		boolean isUpdated = false;

		if (bindingResult.hasErrors()) {
			model.addAttribute("updatableCustomer", customerId);
			return "register";
		}

		if (customerId <= 0) {
			redirect.addFlashAttribute("errorUpdate", "Error updating the customer!");
		}

		// call the update service
		Customer existingCustomer = customerSevice.findCustomerById(customerId);
		if (existingCustomer.getId() > 0) {
			existingCustomer.setUsername(customerDao.getUsername());
			isUpdated = customerSevice.updateCustomer(existingCustomer, customerId);
		}
		if (isUpdated) {
			redirect.addFlashAttribute("updateSuccess", "Customer updated successfully!");
			return "redirect:/admin/user/allUsers";
		}
		return "redirect:/admin/user/allUsers?error=true";

	}
}
