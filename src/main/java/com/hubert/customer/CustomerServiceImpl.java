package com.hubert.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hubert.authority.Authority;
import com.hubert.authority.AuthorityRespository;
import com.hubert.constants.Constants;

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
		if (customerDao != null) {
			Customer cust = new Customer();
			cust.setUsername(customerDao.getUsername());
			cust.setEmail(customerDao.getEmail());
			cust.setDisabled(Constants.IS_DISABLED);

			// encrypt the the password
			String hashPwd = passwordEncoder.encode(customerDao.getPassword());
			cust.setPassword(hashPwd);
			customerRepo.save(cust);

			// automatically add the customer as ROLE_USER
			Authority auth = new Authority();
			auth.setCustomer(cust);
			auth.setName(Constants.ROLE_USER);
			authRepository.save(auth);

			isSaved = true;
		} else {
			isSaved = true;
		}

		return isSaved;
	}

	@Override
	public Customer getCustomerByUsername(String username) {
		Customer cust = null;

		if (username != null) {
			cust = customerRepo.findCustomerByUsername(username);

			if (cust.getId() > 0) {
				return cust;
			}
		}
		return null;
	}

	@Override
	public boolean updateCustomer(Customer cust) {
		boolean isUpdated = false;
		if (cust.getId() > 0) {
			customerRepo.save(cust);
			isUpdated = true;
		}
		return isUpdated;
	}

	// populate all customers
	@Override
	public List<Customer> getAllCustomers() {
		Iterable<Customer> iterableCust = customerRepo.findAll();

		List<Customer> customersList = new ArrayList<>();
		for (Customer c : iterableCust) {
			customersList.add(c);
		}
		return customersList.size() > 0 ? customersList : null;
	}

	// set customer status : ACTIVE : DISABLED
	@Override
	public boolean setCustomerStatus(Long id) {
		boolean isUpdated = false;
		Customer existingCustomer = getCustomer(id);
		if (existingCustomer.getId() > 0) {
			existingCustomer.setDisabled(!existingCustomer.isDisabled());
			updateCustomer(existingCustomer);
			isUpdated = true;
		}
		return isUpdated;
	}

	// DELETE CUSTOMER
	@Override
	public boolean deleteCustomer(Long id) {
		boolean isDeleted = false;
		Customer existingCustomer = getCustomer(id);
		if (existingCustomer.getId() > 0) {
			customerRepo.deleteById(id);
			isDeleted = true;
		}
		return isDeleted;
	}

	private Customer getCustomer(Long id) {
		Optional<Customer> existingCustomer = customerRepo.findById(id);
		if (existingCustomer.isPresent()) {
			return existingCustomer.get();
		}

		return null;
	}

	@Override
	public boolean updateCustomer(Customer customer, Long id) {
		boolean isUpdated = false;

		if (customer.getId() > 0) {
			updateCustomer(customer);
			isUpdated = true;
		}
		return isUpdated;
	}

	// find customer by id
	@Override
	public Customer findCustomerById(Long id) {
		Optional<Customer> existingCustomer = customerRepo.findById(id);
		if (existingCustomer.isPresent()) {
			return existingCustomer.get();
		}
		return null;
	}

}
