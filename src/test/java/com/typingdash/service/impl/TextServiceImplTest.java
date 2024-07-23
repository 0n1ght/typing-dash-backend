package com.typingdash.service.impl;

import com.typingdash.enums.Difficulty;
import com.typingdash.enums.Language;
import com.typingdash.enums.Letter;
import com.typingdash.enums.TextType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TextServiceImplTest {

    @Mock
    private BufferedReader bufferedReaderMock;

    private TextServiceImpl textService;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        when(bufferedReaderMock.readLine())
                .thenReturn("word1")
                .thenReturn("word2")
                .thenReturn("word3")
                .thenReturn(null);

        textService = new TextServiceImpl() {
            private BufferedReader createBufferedReader(Path path) throws IOException {
                return bufferedReaderMock;
            }
        };
    }

    @Test
    public void testGenerateWords() {
        List<String> words = textService.generateText(Language.ENGLISH, Difficulty.EASY, 2, TextType.WORDS);
        assertEquals(2, words.size());
    }

    @Test
    public void testGenerateSentences() {
        List<String> sentences = textService.generateText(Language.ENGLISH, Difficulty.EASY, 2, TextType.SENTENCES);
        assertEquals(2, sentences.size());
    }

    @Test
    public void testGenerateNums() {
        List<Integer> numbers = textService.generateNums(Difficulty.EASY, 5);
        assertEquals(5, numbers.size());
        numbers.forEach(num -> {assert(num >= 0 && num < 100);});
    }

    @Test
    public void testGeneratePracticeWords() {
        List<String> practiceWords = textService.generatePracticeWords(Letter.A, 2);
        assertEquals(2, practiceWords.size());
    }
}
