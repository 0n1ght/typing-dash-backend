package com.typingdash.controller;

import com.typingdash.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getProfile(@PathVariable Long accountId) throws IOException {
        return ResponseEntity.ok(accountService.getProfile(accountId));
    }
}
