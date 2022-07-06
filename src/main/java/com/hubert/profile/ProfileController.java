package com.hubert.profile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hubert.constants.MessageResponse;
import com.hubert.customer.Customer;
import com.hubert.customer.ICustomerService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private IProfileService profileService;
    private ICustomerService customerService;

    @Autowired
    public ProfileController(IProfileService profileService, ICustomerService customerService) {
        this.profileService = profileService;
        this.customerService = customerService;
    }

    @GetMapping("")
    public String displayProfilePage(Model model, HttpSession session,
            @RequestParam(value = "error", required = false) String error) {

        MessageResponse messageResponse = new MessageResponse();
        List<String> generateMessage = null;
        if (error != null) {
            generateMessage = MessageResponse.generateMessage("Current Password is incorrect!", "error");
            model.addAttribute(messageResponse.getMessage(), generateMessage.get(0));
            model.addAttribute(messageResponse.getMessageType(), generateMessage.get(1));
        }

        Profile profile = new Profile();
        Customer cust = (Customer) session.getAttribute("loggedInCustomer");
        model.addAttribute("profile", profile);

        return "pages/profile/profile";
    }

    @PostMapping("/update")
    public String updatePassword(@Valid @ModelAttribute("profile") Profile profile, BindingResult bindingResult,
            Model model, Authentication auth, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "pages/profile/profile";
        }

        // check if the current password is correct
        Customer existingCustomer = customerService.getCustomerByUsername(auth.getName());
        if (!profileService.isPasswordValid(existingCustomer.getPassword(),
                profile.getCurrentPassword())) {

            return "redirect:/profile?error=true";
        } else {
            boolean isUpdated = profileService.updatePassword(profile.getNewPassword());
            if (isUpdated) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
                return "redirect:/login?updated=true";
            }
        }

        // if current password is correct then save the new password but encrypt it
        // first

        return "redirect:/profile/update";
    }
}
