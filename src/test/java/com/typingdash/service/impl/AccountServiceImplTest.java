package com.typingdash.service.impl;

import com.typingdash.dto.SignUpRequest;
import com.typingdash.entity.AccountEntity;
import com.typingdash.mapper.AccountMapper;
import com.typingdash.repo.AccountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    @Mock
    private AccountRepo accountRepo;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUpAccount() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password");
        signUpRequest.setNickname("nick");

        AccountEntity mockAccountEntity = new AccountEntity();
        mockAccountEntity.setId(1L);
        mockAccountEntity.setEmail(signUpRequest.getEmail());
        mockAccountEntity.setPassword(signUpRequest.getPassword());

        when(accountMapper.toAccountEntity(signUpRequest)).thenReturn(mockAccountEntity);

        accountService.signUpAccount(signUpRequest);
        verify(accountMapper, times(1)).toAccountEntity(signUpRequest);
        verify(accountRepo, times(1)).save(mockAccountEntity);
    }


    @Test
    public void testValidateEmail_True() {
        String email = "test@example.com";

        when(accountRepo.existsByEmail(email)).thenReturn(false);

        boolean result = accountService.validateEmail(email);

        verify(accountRepo, times(1)).existsByEmail(email);

        assertTrue(result);
    }

    @Test
    public void testValidateEmail_False() {
        String email = "test@example.com";

        when(accountRepo.existsByEmail(email)).thenReturn(true);

        boolean result = accountService.validateEmail(email);

        verify(accountRepo, times(1)).existsByEmail(email);

        assertFalse(result);
    }
}
