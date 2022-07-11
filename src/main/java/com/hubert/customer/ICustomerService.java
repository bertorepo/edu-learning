package com.hubert.customer;

import java.util.List;

public interface ICustomerService {
	boolean saveCustomer(CustomerDao customerDao);

	Customer getCustomerByUsername(String username);

	boolean updateCustomer(Customer cust);

	boolean setCustomerStatus(Long id);

	boolean deleteCustomer(Long id);

	List<Customer> getAllCustomers();

	boolean updateCustomer(CustomerDao customerDao, Long id);
}
