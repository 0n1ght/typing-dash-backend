package com.typingdash.controller;

import com.typingdash.enums.Difficulty;
import com.typingdash.enums.Language;
import com.typingdash.enums.Letter;
import com.typingdash.service.TextService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TextControllerTest {

    @Mock
    private TextService textService;

    @InjectMocks
    private TextController textController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(textController).build();
    }

    @Test
    @DisplayName("words are generated properly")
    public void testGetWords() throws Exception {
        when(textService.generateWords(Language.ENGLISH, Difficulty.EASY, 3)).thenReturn(List.of("word1", "word2", "word3"));

        mockMvc.perform(get("/text/generate-words/ENGLISH/EASY/3"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("sentences are generated properly")
    public void testGetSentences() throws Exception {
        when(textService.generateSentences(Language.ENGLISH, Difficulty.EASY, 3)).thenReturn(List.of("sentence1", "sentence2", "sentence3"));

        mockMvc.perform(get("/text/generate-sentences/ENGLISH/EASY/3"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("numbers are generated properly")
    public void testGetNumbers() throws Exception {
        when(textService.generateNums(Difficulty.EASY, 3)).thenReturn(List.of(1, 2, 3));

        mockMvc.perform(get("/text/generate-numbers/EASY/3"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("practice words are generated properly")
    public void testGetPracticeWords() throws Exception {
        when(textService.generatePracticeWords(Letter.A, 3)).thenReturn(List.of("apple", "ant", "arrow"));

        mockMvc.perform(get("/text/generate-practice-words/A/3"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("words generation returns exception properly")
    public void testGetWordsException() throws Exception {
        when(textService.generateWords(Language.ENGLISH, Difficulty.EASY, 3)).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/text/generate-words/ENGLISH/EASY/3"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("sentences generation returns exception properly")
    public void testGetSentencesException() throws Exception {
        when(textService.generateSentences(Language.ENGLISH, Difficulty.EASY, 3)).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/text/generate-sentences/ENGLISH/EASY/3"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("numbers generation returns exception properly")
    public void testGetNumbersException() throws Exception {
        when(textService.generateNums(Difficulty.EASY, 3)).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/text/generate-numbers/EASY/3"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("practice words generation returns exception properly")
    public void testGetPracticeWordsException() throws Exception {
        when(textService.generatePracticeWords(Letter.A, 3)).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/text/generate-practice-words/A/3"))
                .andExpect(status().isInternalServerError());
    }
}
