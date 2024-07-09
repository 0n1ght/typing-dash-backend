package com.typingdash.controller;

import com.typingdash.dto.SignUpRequest;
import com.typingdash.entity.AccountEntity;
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


    @PutMapping("/change-email")
    public ResponseEntity<String> changeEmail(@RequestBody Map<String, String> request) {
        try {
            String newEmail = request.get("newEmail");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            AccountEntity accountEntity = accountService.findByEmail(email);
            accountEntity.setEmail(newEmail);

            accountService.updateAccount(accountEntity);

            return ResponseEntity.status(HttpStatus.OK).body("Email changed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error during changing email. Try again later");
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> request) {
        try {
            String newPassword = request.get("newPassword");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            AccountEntity accountEntity = accountService.findByEmail(email);
            accountEntity.setPassword(newPassword);

            accountService.updateAccount(accountEntity);

            return ResponseEntity.status(HttpStatus.OK).body("Password changed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error during changing password. Try again later");
        }
    }


    @PutMapping("/update-nickname")
    public ResponseEntity<String> updateNickname(@RequestBody Map<String, String> request) {
        try {
            String newNickname = request.get("newNickname");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            AccountEntity accountEntity = accountService.findByEmail(email);
            accountEntity.getProfileEntity().setNickname(newNickname);

            accountService.updateAccount(accountEntity);

            return ResponseEntity.status(HttpStatus.OK).body("Nickname updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error during changing nickname. Try again later");
        }
    }

    @PutMapping("/update-lvl")
    public ResponseEntity<String> updateLvl(@RequestBody Map<String, String> request) {
        try {
            String newLvl = request.get("newLvl");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            AccountEntity accountEntity = accountService.findByEmail(email);
            accountEntity.getProfileEntity().setLvl(Integer.parseInt(newLvl));

            accountService.updateAccount(accountEntity);

            return ResponseEntity.status(HttpStatus.OK).body("Level updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error during changing Level. Try again later");
        }
    }

    @PutMapping("/update-highestSpeed")
    public ResponseEntity<String> updateHighestSpeed(@RequestBody Map<String, String> request) {
        try {
            String newHighestSpeed = request.get("newHighestSpeed");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            AccountEntity accountEntity = accountService.findByEmail(email);
            accountEntity.getProfileEntity().setHighestSpeed(Short.parseShort(newHighestSpeed));

            accountService.updateAccount(accountEntity);

            return ResponseEntity.status(HttpStatus.OK).body("Highest Speed updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error during changing Highest Speed. Try again later");
        }
    }

    @PutMapping("/update-testsCompleted")
    public ResponseEntity<String> updateTestsCompleted(@RequestBody Map<String, String> request) {
        try {
            String newTestsCompleted = request.get("newTestsCompleted");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            AccountEntity accountEntity = accountService.findByEmail(email);
            accountEntity.getProfileEntity().setTestsCompleted(Integer.parseInt(newTestsCompleted));

            accountService.updateAccount(accountEntity);

            return ResponseEntity.status(HttpStatus.OK).body("Tests Completed updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error during changing Tests Completed. Try again later");
        }
    }

    @PutMapping("/add-to-history")
    public ResponseEntity<String> addTestToHistory(@RequestBody Map<String, String> request) {
        try {
            String newSpeed = request.get("newSpeed");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            AccountEntity accountEntity = accountService.findByEmail(email);
            accountEntity.getProfileEntity().getLongTestsHistory().add(Short.parseShort(newSpeed));
            accountEntity.getProfileEntity().getShortTestsHistory().add(Short.parseShort(newSpeed));
            if (accountEntity.getProfileEntity().getShortTestsHistory().size() > 20) {
                accountEntity.getProfileEntity().getShortTestsHistory().removeFirst();
            }

            accountService.updateAccount(accountEntity);

            return ResponseEntity.status(HttpStatus.OK).body("New Test added to History");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error during adding New Test to History. Try again later");
        }
    }

    @PutMapping("/update-icon-path")
    public ResponseEntity<String> updateIconPath(@RequestBody Map<String, String> request) {
        try {
            String newIconPath = request.get("newPath");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            AccountEntity accountEntity = accountService.findByEmail(email);
            accountEntity.getProfileEntity().setIconPath(newIconPath);

            accountService.updateAccount(accountEntity);

            return ResponseEntity.status(HttpStatus.OK).body("New Test added to History");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error during adding New Test to History. Try again later");
        }
    }

}
