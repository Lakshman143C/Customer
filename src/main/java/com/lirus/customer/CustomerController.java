package com.lirus.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") Integer id){

        return customerService.getCustomerById(id);
    }
    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest registrationRequest){
        customerService.addCustomer(registrationRequest);
    }
    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable("id") Integer id){
        customerService.deleteCustomerById(id);
    }
    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable("id") Integer id,@RequestBody  CustomerUpdateRequest updateRequest){
        customerService.updateCustomer(updateRequest,id);
    }
}
