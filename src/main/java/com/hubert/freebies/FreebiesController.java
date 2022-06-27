package com.hubert.freebies;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FreebiesController {

	@GetMapping("/freebies")
	public String displayFreebiesPage() {
		return "pages/freebies";
	}
}
