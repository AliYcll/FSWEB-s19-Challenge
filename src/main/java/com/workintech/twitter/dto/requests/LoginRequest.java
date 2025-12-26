package com.workintech.twitter.dto.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email bos olamaz.")
    @Email(message = "Email formati gecersiz.")
    @Size(max = 50, message = "Email en fazla 50 karakter olmali.")
    private String email;

    @NotBlank(message = "Sifre bos olamaz.")
    @Size(max = 255, message = "Sifre en fazla 255 karakter olmali.")
    private String password;
}
