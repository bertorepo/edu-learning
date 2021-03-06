package com.hubert.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	// List<Customer> findCustomerByUsername(String username);
	Customer findCustomerByUsername(String username);
}
