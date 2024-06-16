package com.typingdash.controller;

import com.typingdash.dto.SignUpRequest;
import com.typingdash.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("if sign-up data are correct, account is registered successfully")
    public void testSignUpAccount_Success() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password");
        signUpRequest.setNickname("nickname");

        when(accountService.validateEmail(signUpRequest.getEmail())).thenReturn(true);
        when(accountService.validateNickname(signUpRequest.getNickname())).thenReturn(true);

        ResponseEntity<String> response = accountController.signUpAccount(signUpRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Account created", response.getBody());

        verify(accountService, times(1)).validateEmail(signUpRequest.getEmail());
        verify(accountService, times(1)).signUpAccount(signUpRequest);
    }

    @Test
    @DisplayName("controller returns \"Email is taken\" if validateEmail returns false")
    public void testSignUpAccount_EmailTaken() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password");

        when(accountService.validateEmail(signUpRequest.getEmail())).thenReturn(false);

        ResponseEntity<String> response = accountController.signUpAccount(signUpRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email is taken", response.getBody());

        verify(accountService, times(1)).validateEmail(signUpRequest.getEmail());
        verify(accountService, never()).signUpAccount(any());
    }
}
