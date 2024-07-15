package com.typingdash.mapper;

import com.typingdash.entity.ProfileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileMapper {
    private final ResourceLoader resourceLoader;
    public ProfileEntity toProfileDto(ProfileEntity profile) {
        return profile;
    }
}
