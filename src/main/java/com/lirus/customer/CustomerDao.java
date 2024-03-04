package com.lirus.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Integer id);
    void insertCustomer(Customer customer);
    boolean existsPersonWithEmail(String email);
    boolean existsCustomerById(Integer id);
    void  deleteCustomerById(Integer id);
    void updateCustomer(Customer customer);
}
