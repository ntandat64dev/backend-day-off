package com.masterspring.backenddayoff.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AuthRequest {

    @Email(message = "Invalid email format.")
    @NotNull(message = "Email cannot be null.")
    private String email;

    @Length(min = 8, message = "Password length must be at least 8 characters.")
    @NotNull(message = "Password cannot be null.")
    private String password;

}