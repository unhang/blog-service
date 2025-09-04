package com.hang.blogservice.dto;

import com.hang.blogservice.validator.CreateAccountValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountRequestDto {
    @NotBlank(message = "username is required")
    @Size(min = 1, max = 100, message = "Please set name length in range from 1 to 100")
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 100, message = "Please set password length in range from 8 to 100")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).*$", message = "Password should include character, number and symbol")
    private String password;

    @NotBlank(groups = {CreateAccountValidationGroup.class}, message = "email is required")
    @Email(message = "email should be valid")
    private String email;
}
