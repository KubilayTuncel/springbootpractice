package com.tpe.dto;


import com.tpe.domain.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {

    private Long id;

    @NotNull(message = "First name can not be null")
    @NotBlank(message = "First name can not be space")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotNull(message = "Last name can not be null")
    @NotBlank(message = "Last name can not be space")
    @Size(min = 2, max = 50)
    private String lastName;

    @Email//...@...
    @NotBlank
    private String email;

    private String phone;

    public CustomerDTO (Customer customer){
        this.id = customer.getId();
        this.firstName = customer.getName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
    }
}
