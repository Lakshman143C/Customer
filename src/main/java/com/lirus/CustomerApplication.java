package com.lirus;

import com.github.javafaker.Faker;
import com.lirus.customer.Customer;
import com.lirus.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;


@SpringBootApplication
public class CustomerApplication {


	public static void main(String[] args) {

		SpringApplication.run(CustomerApplication.class, args);

	}

	@Bean
	CommandLineRunner runner(CustomerRepository customerRepository){
		return args -> {
			Faker faker=new Faker();
			Random random=new Random();
			String fname=faker.name().firstName();
			String lname=faker.name().lastName();
			Customer customer=new Customer(
					fname+" "+lname,
					fname+"."+lname+"."+"@lirus.com",
					random.nextInt(16,40)
			);
			customerRepository.save(customer);
		};

	}


}
