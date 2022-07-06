package com.hubert.freebies;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FreebiesController {

	private Ifreebies freebieService;

	@Autowired
	public FreebiesController(Ifreebies freebieService) {
		this.freebieService = freebieService;
	}

	@GetMapping("/freebies")
	public String displayFreebiesPage() {
		return "pages/freebies/freebies";
	}

	@GetMapping("/admin/freebies/addFreebies")
	public String addFreebies(Model model) {

		model.addAttribute("freebiesDao", new FreebiesDao());
		return "pages/freebies/add-freebies";
	}

	@PostMapping("/admin/freebies/addFreebies")
	public String saveFreebie(@Valid @ModelAttribute("freebiesDao") FreebiesDao freebiesDao,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "pages/freebies/add-freebies";
		}

		// call the service
		Freebies myFreebie = freebieService.saveFreebie(freebiesDao);
		if (myFreebie.getId() > 0) {
			return "redirect:/freebies?add=true";
		}

		return "redirect:/freebies?add=false";
	}
}
