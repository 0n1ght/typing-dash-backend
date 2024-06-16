package com.typingdash.controller;

import com.typingdash.entity.ProfileEntity;
import com.typingdash.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final AccountService accountService;
    @GetMapping("/top50")
    public List<ProfileEntity> getTop50() {
        return accountService.getTop50ProfilesByCurrentSpeed();
    }
}
