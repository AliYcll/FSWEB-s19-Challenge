package com.workintech.twitter.service;

import com.workintech.twitter.dto.requests.LoginRequest;
import com.workintech.twitter.dto.requests.RegisterRequest;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.UserRepository;
import com.workintech.twitter.security.JwtService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_savesUserWithEncodedPassword() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("john");
        request.setEmail("john@example.com");
        request.setPassword("secret");
        request.setDisplayName("John");

        when(passwordEncoder.encode("secret")).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User saved = authService.register(request);

        Assertions.assertEquals("hashed", saved.getPasswordHash());
        Assertions.assertEquals("john", saved.getUsername());
        Assertions.assertEquals("john@example.com", saved.getEmail());
    }

    @Test
    void login_returnsTokenWhenCredentialsMatch() {
        LoginRequest request = new LoginRequest();
        request.setEmail("john@example.com");
        request.setPassword("secret");

        User user = new User();
        user.setEmail("john@example.com");
        user.setPasswordHash("hashed");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret", "hashed")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwt-token");

        String token = authService.login(request);

        Assertions.assertEquals("jwt-token", token);
        verify(jwtService).generateToken(user);
    }

    @Test
    void login_throwsWhenCredentialsInvalid() {
        LoginRequest request = new LoginRequest();
        request.setEmail("john@example.com");
        request.setPassword("wrong");

        User user = new User();
        user.setEmail("john@example.com");
        user.setPasswordHash("hashed");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "hashed")).thenReturn(false);

        Assertions.assertThrows(RuntimeException.class, () -> authService.login(request));
    }
}
