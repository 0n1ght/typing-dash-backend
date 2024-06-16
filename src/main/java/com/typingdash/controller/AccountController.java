package com.typingdash.controller;

import com.typingdash.dto.SignUpRequest;
import com.typingdash.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("sign-up")
    public ResponseEntity<String> signUpAccount(@RequestBody SignUpRequest signUpRequest) {

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

    //todo data update
//    @PutMapping("/update-nickname")
//    public String updateNickname(@RequestBody String newNickname) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentUsername = authentication.getName();
//
//        User user = userService.findByUsername(currentUsername);
//        if (user != null) {
//            user.setNickname(newNickname);
//            userService.save(user);
//            return "Nickname updated successfully";
//        } else {
//            return "User not found";
//        }
//    }

}
