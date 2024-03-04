package com.lirus.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerUpdateRequest {
    String name;
    String email;
    Integer age;
}
