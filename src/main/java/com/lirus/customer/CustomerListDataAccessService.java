package com.lirus.customer;


import com.lirus.exception.ResourceNotFound;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao{
    static List<Customer> customers;
    static {
        customers=new ArrayList<>();
        Customer alex=new Customer(
                "alex",
                "alex@lirus.com",
                22
        );
        Customer jamila=new Customer(
                "Jamila",
                "Jamila@lirus.com",
                22
        );
        customers.add(alex);
        customers.add(jamila);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> getCustomerById(Integer id) {
        return customers.stream()
                .filter(c->c.getId().equals(id))
                .findFirst();

    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream()
                .anyMatch(c-> c.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerById(Integer id) {
        return customers.stream().anyMatch(customer -> customer.getId().equals(id));
    }

    @Override
    public void deleteCustomerById(Integer id) {
        Customer c=customers.stream().filter(customer -> customer.getId().equals(id))
                .findFirst().orElseThrow(()->new ResourceNotFound("Customer not found with given id"));
        customers.remove(c);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customers.add(customer);
    }
}
