package com.hubert.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hubert.customer.Customer;
import com.hubert.customer.ICustomerService;

@Service
public class ProfileServiceImpl implements IProfileService {

    private PasswordEncoder passwordEncoder;
    private ICustomerService customerService;

    @Autowired
    public ProfileServiceImpl(PasswordEncoder passwordEncoder,
            ICustomerService customerService) {

        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
    }

    @Override
    public boolean isPasswordValid(String currentPassword, String rawPassword) {
        boolean isValid = false;
        boolean checkIfValid = checkIfPasswordIsValid(currentPassword, rawPassword);

        if (checkIfValid) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public boolean updatePassword(String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isUpdated = false;

        if (newPassword != null) {
            Customer oldCustomer = customerService.getCustomerByUsername(auth.getName());

            // encrypt the raw Password
            String hashPwd = passwordEncoder.encode(newPassword);
            oldCustomer.setPassword(hashPwd);
            customerService.updateCustomer(oldCustomer);
            isUpdated = true;
        }
        return isUpdated;
    }

    private boolean checkIfPasswordIsValid(String currentPass, String newPass) {
        boolean isValid = false;
        if (newPass != null && currentPass != null) {
            if (passwordEncoder.matches(newPass, currentPass)) {
                isValid = true;
            } else {
                isValid = false;
            }
        }

        return isValid;
    }

}
