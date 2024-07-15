package com.typingdash.service;

import com.typingdash.dto.SignUpRequest;
import com.typingdash.entity.AccountEntity;
import com.typingdash.entity.ProfileEntity;

import java.io.IOException;
import java.util.List;

public interface AccountService {
    void signUpAccount(SignUpRequest signUpRequest);
    boolean validateEmail(String email);
    boolean validateNickname(String nick);
    ProfileEntity getProfile(Long accountId) throws IOException;
    List<ProfileEntity> getTop50ProfilesByCurrentSpeed();
    AccountEntity findByEmail(String email);
    void updateAccount(AccountEntity accountEntity);
    int generateToken(String email);
    void changePassword(String email, String password);
}
