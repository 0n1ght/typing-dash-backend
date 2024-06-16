package com.typingdash.mapper;

import com.typingdash.entity.ProfileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class ProfileMapper {
    private final ResourceLoader resourceLoader;
    public ProfileEntity toProfileDto(ProfileEntity profile) throws IOException {
        if (profile.getIconPath() != null) {
            Resource resource = resourceLoader.getResource("classpath:" + profile.getIconPath());
            byte[] imageBytes = Files.readAllBytes(Paths.get(resource.getURI()));
            profile.setIconPath(Base64.getEncoder().encodeToString(imageBytes));
        }
        return profile;
    }
}
