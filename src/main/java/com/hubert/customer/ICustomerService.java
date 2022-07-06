package com.hubert.customer;

public interface ICustomerService {
	boolean saveCustomer(CustomerDao customerDao);

	Customer getCustomerByUsername(String username);

	boolean updateCustomer(Customer cust);
}
