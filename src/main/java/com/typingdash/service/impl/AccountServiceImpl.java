package com.typingdash.service.impl;

import com.typingdash.dto.SignUpRequest;
import com.typingdash.entity.AccountEntity;
import com.typingdash.entity.ProfileEntity;
import com.typingdash.mapper.AccountMapper;
import com.typingdash.mapper.ProfileMapper;
import com.typingdash.repo.AccountRepo;
import com.typingdash.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepo accountRepo;
    private final AccountMapper accountMapper;
    private final ProfileMapper profileMapper;

    @Override
    public void signUpAccount(SignUpRequest signUpRequest) {
        AccountEntity account = accountMapper.toAccountEntity(signUpRequest);
        accountRepo.save(account);
        logger.info("new account created: " + account);
    }

    @Override
    public boolean validateEmail(String email) {
        return !accountRepo.existsByEmail(email);
    }

    @Override
    public boolean validateNickname(String nick) {
        return !accountRepo.existsByNickname(nick);
    }

    @Override
    public ProfileEntity getProfile(Long accountId) throws IOException {
        return profileMapper.toProfileDto(accountRepo.findById(accountId)
                .map(AccountEntity::getProfileEntity)
                .orElseThrow(() -> new NoSuchElementException("Account not found with id: " + accountId)));
    }

    @Override
    public List<ProfileEntity> getTop50ProfilesByCurrentSpeed() {
        return accountRepo.findTop50ProfilesByOrderByCurrentSpeedDesc();
    }

    public void changeNick(String email, String newNick) {
        AccountEntity accountEntity = accountRepo.findByEmail(email).orElseThrow();
        accountEntity.getProfileEntity().setNickname(newNick);
        accountRepo.save(accountEntity);
    }

    @Override
    public AccountEntity findByEmail(String email) {
        return accountRepo.findByEmail(email).orElseThrow();
    }

    @Override
    public void updateAccount(AccountEntity accountEntity) {
        accountRepo.save(accountEntity);
    }
}
