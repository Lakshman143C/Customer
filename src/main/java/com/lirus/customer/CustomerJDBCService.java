package com.lirus.customer;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCService implements CustomerDao{
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql= """
                    SELECT id,name,email,age from customer;
                """;
       return jdbcTemplate.query(sql,customerRowMapper);
    }

    @Override
    public Optional<Customer> getCustomerById(Integer id) {
        String sql= """
                    SELECT id,name,email,age from customer WHERE id=?;
                """;
        return jdbcTemplate
                .query(sql,customerRowMapper,id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        String sql= """
                    INSERT INTO customer (name,email,age)
                    VALUES(?,?,?);
                """;
        int res=jdbcTemplate.update(sql,customer.getName(),customer.getEmail(),customer.getAge());
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        String sql= """
                    SELECT COUNT(id) from customer WHERE email=?;
                """;
        Integer count=jdbcTemplate.queryForObject(sql, Integer.class,email);
        return count !=null && count>0;
    }

    @Override
    public boolean existsCustomerById(Integer id) {
        String sql= """
                    SELECT COUNT(id) from customer WHERE id=?;
                """;
        Integer count=jdbcTemplate.queryForObject(sql, Integer.class,id);
        return count !=null && count>0;
    }

    @Override
    public void deleteCustomerById(Integer id) {
        String sql= """
                    DELETE FROM customer where id=?
                """;
        jdbcTemplate.update(sql,id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        if(customer.getName()!=null){
            String sql="UPDATE customer SET name=? WHERE id=?";
           jdbcTemplate.update(sql,customer.getName(),customer.getId());
        }
        if(customer.getEmail()!=null){
            String sql="UPDATE customer SET email=? WHERE id=?";
            jdbcTemplate.update(sql,customer.getEmail(),customer.getId());
        }
        if(customer.getName()!=null){
            String sql="UPDATE customer SET age=? WHERE id=?";
            jdbcTemplate.update(sql,customer.getAge(),customer.getId());
        }
    }
}
