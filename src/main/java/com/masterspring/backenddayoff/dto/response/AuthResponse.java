package com.masterspring.backenddayoff.dto.response;

import com.masterspring.backenddayoff.entity.Department;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthResponse {

    private Long id;

    @Email
    private String email;

    private String fullName;

    private LocalDate birthdate;

    private LocalDate workDate;

    private String phone;

    private String address;

    private Integer role;

    private Department department;

    private Integer remainDays;
}
