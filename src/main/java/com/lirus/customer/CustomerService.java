package com.lirus.customer;

import com.lirus.exception.DuplicateResourceException;
import com.lirus.exception.RequestValidationException;
import com.lirus.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    List<Customer> getCustomers(){
        return customerDao.getAllCustomers();
    }
    Customer getCustomerById(Integer id){
        return customerDao.getCustomerById(id).orElseThrow(()-> new ResourceNotFound("Customer not found with the given id"));
    }
    public void addCustomer(CustomerRegistrationRequest registrationRequest){
        //check if email exiss or not
        if(customerDao.existsPersonWithEmail(registrationRequest.getEmail())){
            throw  new DuplicateResourceException("Email already taken");
        }
        Customer customer=new Customer(
            registrationRequest.getName(),
                registrationRequest.getEmail(),
                registrationRequest.getAge()
        );
        customerDao.insertCustomer(customer);
    }
    public void deleteCustomerById(Integer id){
        if(!customerDao.existsCustomerById(id))
            throw new ResourceNotFound("Customer with given id not exists");
        customerDao.deleteCustomerById(id);
    }
    public void updateCustomer(CustomerUpdateRequest updateRequest ,Integer id){
        //get the customer with given id
        Customer customerUpdate=getCustomerById(id);
        boolean isChangesExists=false;
        if(updateRequest.getName()!=null && !updateRequest.getName().equals(customerUpdate.getName())){
            customerUpdate.setName(updateRequest.getName());
            isChangesExists=true;
        }
        if(updateRequest.getAge()!=null && !updateRequest.getAge().equals(customerUpdate.getAge())){
            customerUpdate.setAge(updateRequest.getAge());
            isChangesExists=true;
        }
        if(updateRequest.getEmail()!=null && !updateRequest.getEmail().equals(customerUpdate.getEmail())){
            if(customerDao.existsPersonWithEmail(updateRequest.getEmail()))
                throw  new RequestValidationException("email already taken");
            customerUpdate.setEmail(updateRequest.getEmail());
            isChangesExists=true;
        }
        if(!isChangesExists)
            throw new RequestValidationException("No changes found");
        customerDao.updateCustomer(customerUpdate);
    }
}
