package com.workintech.twitter.dto.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    @Size(min = 3, max = 50, message = "Username 3-50 karakter olmali.")
    private String username;

    @NotBlank(message = "Email bos olamaz.")
    @Email(message = "Email formati gecersiz.")
    @Size(max = 50, message = "Email en fazla 50 karakter olmali.")
    private String email;

    @NotBlank(message = "Sifre bos olamaz.")
    @Size(min = 6, max = 255, message = "Sifre 6-255 karakter olmali.")
    private String password;

    @NotBlank(message = "Display name bos olamaz.")
    @Size(max = 50, message = "Display name en fazla 50 karakter olmali.")
    private String displayName;
}
