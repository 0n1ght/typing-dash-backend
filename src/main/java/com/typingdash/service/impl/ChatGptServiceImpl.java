package com.typingdash.service.impl;

import com.typingdash.service.ChatGptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ChatGptServiceImpl implements ChatGptService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String generateText(String topic) {
        String prompt = "Write a summary about " + topic;

        // Ustawienie nagłówków HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiApiKey);
        headers.set("Content-Type", "application/json");

        // Utworzenie żądania HTTP
        String requestBody = "{\"model\":\"text-davinci-003\",\"prompt\":\"" + prompt + "\",\"max_tokens\":150}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Użycie RestTemplate do wysłania żądania POST do OpenAI API
        String openaiEndpoint = "https://api.openai.com/v1/completions";
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(openaiEndpoint, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            log.error("Błąd podczas wywoływania API OpenAI: " + e.getMessage());
            return "Error generating text: " + e.getMessage();
        }

        // Obsługa odpowiedzi
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            log.error("Błąd podczas wywoływania API OpenAI: " + response.getStatusCode());
            return "Error generating text: Unexpected status code " + response.getStatusCode();
        }
    }
}
