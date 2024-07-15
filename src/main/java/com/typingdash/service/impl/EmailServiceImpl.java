package com.typingdash.service.impl;

import com.typingdash.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendToken(int token) {
        //todo send email
        System.out.println("Password reset token: " + token);
    }
}
