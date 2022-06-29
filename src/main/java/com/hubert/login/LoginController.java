package com.hubert.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hubert.constants.MessageResponse;

@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "register", required = false) String register,
			@RequestParam(value = "error", required = false) String error, Model model) {
		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if(auth !=null) {
//			return "redirect:/dashboard";
//		}

		MessageResponse messageResponse = new MessageResponse();
		List<String> generateMessage = null;
		if (logout != null) {
			generateMessage = MessageResponse.generateMessage("You logout successfully!", "success");
			model.addAttribute(messageResponse.getMessage(), generateMessage.get(0));
			model.addAttribute(messageResponse.getMessageType(), generateMessage.get(1));
		} else if (register != null) {
			generateMessage = MessageResponse.generateMessage("Registration successful. You can now login!", "success");
			model.addAttribute(messageResponse.getMessage(), generateMessage.get(0));
			model.addAttribute(messageResponse.getMessageType(), generateMessage.get(1));
		} else if (error != null) {
			generateMessage = MessageResponse.generateMessage("Invalid username and password", "error");
			model.addAttribute(messageResponse.getMessage(), generateMessage.get(0));
			model.addAttribute(messageResponse.getMessageType(), generateMessage.get(1));
		}
		return "/login";
	}

	@GetMapping("/logout")
	public String loogut(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout=true";
	}
}
