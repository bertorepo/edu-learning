package com.hubert.customer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hubert.authority.Authority;
import com.hubert.authority.AuthorityRespository;

@Service
public class CustomerServiceImpl implements ICustomerService {

	private CustomerRepository customerRepo;
	private AuthorityRespository authRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepo, AuthorityRespository authRepository,
			PasswordEncoder passwordEncoder) {
		this.customerRepo = customerRepo;
		this.authRepository = authRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public boolean saveCustomer(CustomerDao customerDao) {
		boolean isSaved = false;

		// adding the new customer
		ModelMapper modelMap = new ModelMapper();
		if (customerDao != null) {
			Customer cust = modelMap.map(customerDao, Customer.class);

			// encrypt the the password
			String hashPwd = passwordEncoder.encode(cust.getPassword());
			cust.setPassword(hashPwd);
			customerRepo.save(cust);

			// automatically add the customer as ROLE_USER
			Authority auth = new Authority();
			auth.setCustomer(cust);
			auth.setName("ROLE_USER");
			authRepository.save(auth);

			isSaved = true;
		} else {
			isSaved = true;
		}

		return isSaved;
	}

}
