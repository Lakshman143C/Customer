package com.lirus.customer;

import com.lirus.AbstractTestContainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerJDBCServiceTest extends AbstractTestContainers {
    private CustomerJDBCService underTest;
    private CustomerRowMapper customerRowMapper=new CustomerRowMapper();


    @BeforeEach
    void setUp() {
        underTest=new CustomerJDBCService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void getAllCustomers() {
        //Given
        Customer c=new Customer(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress()+ UUID.randomUUID(),
                20
        );
        underTest.insertCustomer(c);
        //When
        List<Customer> allCustomers = underTest.getAllCustomers();
        //Then
        assertThat(allCustomers).isNotEmpty();
    }

    @Test
    void getCustomerById() {
        //Given
        String email=FAKER.internet().safeEmailAddress()+ UUID.randomUUID();
        Customer c=new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(c);

        int id=underTest.getAllCustomers()
                .stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        Optional<Customer> actual = underTest.getCustomerById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(cs->{
            assertThat(cs.getId()).isEqualTo(id);
            assertThat(cs.getName()).isEqualTo(c.getName());
            assertThat(cs.getEmail()).isEqualTo(c.getEmail());
            assertThat(cs.getAge()).isEqualTo(c.getAge());
        });

    }

    @Test
    void WillReturnEmptyWhenSelectCustomerById() {
        //Given
        int id=-1;
        //When
        Optional<Customer> actual = underTest.getCustomerById(id);
        //Then
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        //Given

        //When

        //Then
    }

    @Test
    void existsPersonWithEmail() {
        //Given

        //When

        //Then
    }

    @Test
    void existsCustomerById() {
        //Given

        //When

        //Then
    }

    @Test
    void deleteCustomerById() {
        //Given

        //When

        //Then
    }

    @Test
    void updateCustomer() {
        //Given

        //When

        //Then
    }
}