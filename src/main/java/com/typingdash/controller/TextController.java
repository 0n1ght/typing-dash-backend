package com.typingdash.controller;

import com.typingdash.enums.Difficulty;
import com.typingdash.enums.Language;
import com.typingdash.enums.Letter;
import com.typingdash.service.ChatGptService;
import com.typingdash.service.TextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/text")
@RequiredArgsConstructor
public class TextController {
    private final TextService textService;
    private final ChatGptService chatGptService;

    @GetMapping("/generate-words/{lang}/{difficulty}/{len}")
    public ResponseEntity<?> getWords(@PathVariable Language lang, @PathVariable Difficulty difficulty, @PathVariable int len) {

        try {
            return ResponseEntity.ok(textService.generateWords(lang, difficulty, len));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating words: \" + e.getMessage()");
        }
    }

    @GetMapping("/generate-sentences/{lang}/{difficulty}/{len}")
    public ResponseEntity<?> getSentences(@PathVariable Language lang, @PathVariable Difficulty difficulty, @PathVariable int len) {

        try {
            return ResponseEntity.ok(textService.generateSentences(lang, difficulty, len));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating sentences: \" + e.getMessage()");
        }
    }

    @GetMapping("/generate-numbers/{difficulty}/{len}")
    public ResponseEntity<?> getNumbers(@PathVariable Difficulty difficulty, @PathVariable int len) {

        try {
            return ResponseEntity.ok(textService.generateNums(difficulty, len));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating numbers: \" + e.getMessage()");
        }
    }

    @GetMapping("/generate-practice-words/{letter}/{len}")
    public ResponseEntity<?> getPracticeWords(@PathVariable Letter letter, @PathVariable  int len) {

        try {
            return ResponseEntity.ok(textService.generatePracticeWords(letter, len));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating words: \" + e.getMessage()");
        }
    }

    @GetMapping("/generate-topic-text")
    public ResponseEntity<String> generateText(@RequestParam String topic) {
        try {
            String response = chatGptService.generateText(topic);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error generating text: " + e.getMessage());
        }
    }
}
