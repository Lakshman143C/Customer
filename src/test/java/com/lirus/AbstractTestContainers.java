package com.lirus;

import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;


@Testcontainers
public abstract class AbstractTestContainers {

    @BeforeAll
    static void beforeAll() {
        Flyway flyway=Flyway
                .configure()
                .dataSource(
                        pcontainer.getJdbcUrl(),
                        pcontainer.getUsername(),
                        pcontainer.getPassword())
                .load();
        flyway.migrate();
        System.out.println("Migrations application test case passed");
    }

    @Container
    protected final static PostgreSQLContainer<?> pcontainer=
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("lirus")
                    .withUsername("lirus")
                    .withPassword("lirus");

    @DynamicPropertySource
    private static  void registerDataSourceProperties(DynamicPropertyRegistry registry){
        registry.add(
                "spring.datasource.url",
                pcontainer::getJdbcUrl
        );
        registry.add(
                "spring.datasource.username",  //laxman 3-mar-2024
                pcontainer::getUsername               // at the time of testing we need to pass the datasoure
        );                                            // and here we are passing the test container properties
        registry.add(                                 // so that against this db we can run test
                "spring.datasource.password",
                pcontainer::getPassword
        );
    }

    protected static DataSource getDatasource(){
        return DataSourceBuilder.create()
                .driverClassName(pcontainer.getDriverClassName())
                .url(pcontainer.getJdbcUrl())
                .username(pcontainer.getUsername())
                .password(pcontainer.getPassword())
                .build();
        //return builder.build();
    }

    protected static JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDatasource());
    }
    protected static final Faker FAKER=new Faker();
}
