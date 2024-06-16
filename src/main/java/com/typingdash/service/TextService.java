package com.typingdash.service;

import com.typingdash.enums.Difficulty;
import com.typingdash.enums.Language;
import com.typingdash.enums.Letter;

import java.io.IOException;
import java.util.List;

public interface TextService {
    List<String> generateWords(Language lang, Difficulty difficulty, int len);
    List<String> generateSentences(Language lang, Difficulty difficulty, int len);
    String generateTopicWords(Language lang, Difficulty difficulty, int len, String topic) throws IOException;
    String generateTopicSentences(Language lang, Difficulty difficulty, int len, String topic) throws IOException;
    List<Integer> generateNums(Difficulty difficulty, int len);
    List<String> generatePracticeWords(Letter letter, int len);
}
