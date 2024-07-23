package com.typingdash.service;

import com.typingdash.enums.*;

import java.io.IOException;
import java.util.List;

public interface TextService {
    List<String> generateText(Language lang, Difficulty difficulty, int len, TextType textType);
    List<String> generateCodingText(CodingLanguage lang, int len, TextType textType);
    String generateTopicText(Language lang, Difficulty difficulty, int len, String topic, TextType textType) throws IOException;
    List<Integer> generateNums(Difficulty difficulty, int len);
    List<String> generatePracticeWords(Letter letter, int len);
}
