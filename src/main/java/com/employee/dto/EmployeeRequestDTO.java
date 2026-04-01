package com.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeRequestDTO {

    @NotNull
    private String firstName;

    private String lastName;

    @Email
    @NotNull
    private String email;

    private String department;

    private Double salary;
}
