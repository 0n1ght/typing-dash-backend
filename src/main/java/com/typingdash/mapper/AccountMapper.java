package com.typingdash.mapper;

import com.typingdash.dto.SignUpRequest;
import com.typingdash.entity.AccountEntity;
import com.typingdash.entity.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountEntity toAccountEntity(SignUpRequest signUpRequest) {
        AccountEntity account = new AccountEntity();
        account.setEmail(signUpRequest.getEmail());
        account.setPassword(signUpRequest.getPassword());
        account.setProfileEntity(new ProfileEntity(signUpRequest.getNickname()));
        return account;
    }
}
