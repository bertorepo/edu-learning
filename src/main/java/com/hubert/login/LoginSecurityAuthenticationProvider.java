package com.hubert.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hubert.authority.Authority;
import com.hubert.customer.Customer;
import com.hubert.customer.CustomerRepository;

@Component
public class LoginSecurityAuthenticationProvider implements AuthenticationProvider {

	private CustomerRepository customerRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public LoginSecurityAuthenticationProvider(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();

		// List<Customer> customersList =
		// customerRepository.findCustomerByUsername(username);

		Customer myCustomer = customerRepository.findCustomerByUsername(username);

		// check if customer exist
		if (myCustomer == null) {
			throw new UsernameNotFoundException("Username not Found in the Database!");
		}

		// continue if customer exist
		if (myCustomer.getId() > 0) {
			if (!myCustomer.isDisabled()) {
				if (passwordEncoder.matches(pwd, myCustomer.getPassword())) {
					return new UsernamePasswordAuthenticationToken(username, pwd,
							getGrantedAuthority(myCustomer.getAuthorities()));
				} else {
					throw new UsernameNotFoundException("Invalid Credentials!");
				}
			} else {
				throw new UsernameNotFoundException("Disabled!");
			}
		} else {
			throw new UsernameNotFoundException("Username not Found in the Database!");
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	private List<GrantedAuthority> getGrantedAuthority(Set<Authority> authorities) {
		List<GrantedAuthority> authorityList = new ArrayList<>();
		for (Authority authority : authorities) {
			authorityList.add(new SimpleGrantedAuthority(authority.getName()));
		}

		return authorityList;
	}

}
