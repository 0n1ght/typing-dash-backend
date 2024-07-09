package com.typingdash.controller;

import com.typingdash.dto.SignUpRequest;
import com.typingdash.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("sign-up")
    public ResponseEntity<String> signUpAccount(@NotNull @RequestBody SignUpRequest signUpRequest) {

        if (!accountService.validateEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is taken");

        } else if (!accountService.validateNickname(signUpRequest.getNickname())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nickname is taken");
        }

        try {
            accountService.signUpAccount(signUpRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Account created");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during creation");
        }
    }

    //todo change acc and profile data
    @PutMapping("/change-nickname")
    public ResponseEntity<String> updateNickname(@RequestBody Map<String, String> request) {
        try {
            String newNickname = request.get("newNickname");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            accountService.changeNick(email, newNickname);

            return ResponseEntity.status(HttpStatus.OK).body("Nickname changed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error during changing nickname. Try again later");
        }
    }

    @PutMapping("/change-nickname")
    public ResponseEntity<String> changeEmail(@RequestBody Map<String, String> request) {
        try {
            String newNickname = request.get("newNickname");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            accountService.changeNick(email, newNickname);

            return ResponseEntity.status(HttpStatus.OK).body("Nickname changed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error during changing nickname. Try again later");
        }
    }

    @PutMapping("/change-nickname")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> request) {
        try {
            String newNickname = request.get("newNickname");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            accountService.changeNick(email, newNickname);

            return ResponseEntity.status(HttpStatus.OK).body("Nickname changed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error during changing nickname. Try again later");
        }
    }


}
