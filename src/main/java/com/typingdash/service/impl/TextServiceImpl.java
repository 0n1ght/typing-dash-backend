package com.typingdash.service.impl;

import com.typingdash.enums.*;
import com.typingdash.service.TextService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

@Service
public class TextServiceImpl implements TextService {
    private final HashMap<Language, Map<Difficulty, LinkedList<String>>> generalWords = new HashMap<>();
    private final Map<Language, Map<Difficulty, LinkedList<String>>> generalSentences = new HashMap<>();
    private final Map<CodingLanguage, LinkedList<String>> codingWords = new HashMap<>();
    private final Map<CodingLanguage, LinkedList<String>> codingStatements = new HashMap<>();
    private final Map<Letter, List<String>> practiceWords = new HashMap<>();

    public TextServiceImpl() throws IOException {
        initializeGeneralTexts();
        initializeCodingTexts();
        initializePracticeWords();
    }


    private void initializeGeneralTexts() throws IOException {

        for (Language lang : Language.values()) {
            addWordsSet(lang, Path.of("src/main/resources/words/"+lang.toString().toLowerCase()));
            addSentencesSet(lang, Path.of("src/main/resources/sentences/"+lang.toString().toLowerCase()));
        }
    }

    //TODO: addWordsSet+addSentencesSet = addGeneralText(TextType textType)
    private void addWordsSet(Language language, Path generalPath) throws IOException {
        Map<Difficulty, LinkedList<String>> words = new HashMap<>();

        BufferedReader reader;
        String line;

        //easy
        LinkedList<String> easyWords = new LinkedList<>();
        reader = new BufferedReader(new FileReader(generalPath+"/easyWords.txt"));
        while ((line = reader.readLine()) != null) {
            easyWords.add(line.strip());
        }
        words.put(Difficulty.EASY, easyWords);
        //medium
        LinkedList<String> mediumWords = new LinkedList<>();
        reader = new BufferedReader(new FileReader(generalPath+"/mediumWords.txt"));
        while ((line = reader.readLine()) != null) {
            mediumWords.add(line.strip());
        }
        words.put(Difficulty.MEDIUM, mediumWords);
        //hard
        LinkedList<String> hardWords = new LinkedList<>();
        reader = new BufferedReader(new FileReader(generalPath+"/hardWords.txt"));
        while ((line = reader.readLine()) != null) {
            hardWords.add(line.strip());
        }
        words.put(Difficulty.HARD, hardWords);

        this.generalWords.put(language, words);
    }

    private void addSentencesSet(Language language, Path generalPath) throws IOException {
        Map<Difficulty, LinkedList<String>> sentences = new HashMap<>();

        BufferedReader reader;
        String line;

        //easy
        LinkedList<String> easySentences = new LinkedList<>();
        reader = new BufferedReader(new FileReader(generalPath+"/easySentences.txt"));
        while ((line = reader.readLine()) != null) {
            easySentences.add(line.strip());
        }
        sentences.put(Difficulty.EASY, easySentences);
        //medium
        LinkedList<String> mediumSentences = new LinkedList<>();
        reader = new BufferedReader(new FileReader(generalPath+"/mediumSentences.txt"));
        while ((line = reader.readLine()) != null) {
            mediumSentences.add(line.strip());
        }
        sentences.put(Difficulty.MEDIUM, mediumSentences);
        //hard
        LinkedList<String> hardSentences = new LinkedList<>();
        reader = new BufferedReader(new FileReader(generalPath+"/hardSentences.txt"));
        while ((line = reader.readLine()) != null) {
            hardSentences.add(line.strip());
        }
        sentences.put(Difficulty.HARD, hardSentences);

        this.generalSentences.put(language, sentences);
    }


    private void initializeCodingTexts() throws IOException {

        for (CodingLanguage lang : CodingLanguage.values()) {
            addCodingText(lang, Path.of("src/main/resources/coding/keywords/"+lang.toString().toLowerCase()+".txt"), TextType.WORDS);
            addCodingText(lang, Path.of("src/main/resources/coding/statements/"+lang.toString().toLowerCase()+".txt"), TextType.SENTENCES);
        }
    }

    private void addCodingText(CodingLanguage language, Path generalPath, TextType textType) throws IOException {

        BufferedReader reader;
        String line;

        LinkedList<String> textsToAdd = new LinkedList<>();
        reader = new BufferedReader(new FileReader(generalPath+""));
        while ((line = reader.readLine()) != null) {
            textsToAdd.add(line.strip());
        }

        switch(textType) {
            case WORDS -> this.codingWords.put(language, textsToAdd);
            case SENTENCES -> this.codingStatements.put(language, textsToAdd);
        }
    }


    private void initializePracticeWords() throws IOException {
        for (Letter letter : Letter.values()) {
            addPracticeWordsSet(letter, Path.of("src/main/resources/practice/" + letter.getValue() + ".txt"));
        }
    }

    private void addPracticeWordsSet(Letter letter, Path path) throws IOException {
        LinkedList<String> practiceWords = new LinkedList<>();

        BufferedReader reader = new BufferedReader(new FileReader(path.toString()));
        String line;
        while ((line = reader.readLine()) != null) {
            practiceWords.add(line.strip());
        }

        this.practiceWords.put(letter, practiceWords);
    }


    @Override
    public List<String> generateText(Language lang, Difficulty difficulty, int len, TextType textType) {
        List res = new LinkedList();

        List<String> textList = switch (textType) {
            case WORDS  -> generalWords.get(lang).get(difficulty);
            case SENTENCES -> generalSentences.get(lang).get(difficulty);
        };
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            res.add(textList.get(random.nextInt(textList.size())));
        }

        return res;
    }

    @Override
    public List<String> generateCodingText(CodingLanguage lang, int len, TextType textType) {
        List res = new LinkedList();

        List<String> textList = switch (textType) {
            case WORDS  -> codingWords.get(lang);
            case SENTENCES -> codingStatements.get(lang);
        };
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            res.add(textList.get(random.nextInt(textList.size())));
        }

        return res;
    }


    @Override
    public String generateTopicText(Language lang, Difficulty difficulty, int len, String topic, TextType textType) { //todo
        return null;
    }


    @Override
    public List<Integer> generateNums(Difficulty difficulty, int len) {
        List<Integer> res = new LinkedList<>();

        Random random = new Random();
        if (difficulty == Difficulty.EASY) {
            for (int i = 0; i < len; i++) {
                res.add(random.nextInt(100));
            }
        } else if (difficulty == Difficulty.MEDIUM) {
            for (int i = 0; i < len; i++) {
                res.add(random.nextInt(10_000) + 100);
            }
        } else {
            for (int i = 0; i < len; i++) {
                res.add(random.nextInt(1_000_000_000) + 1_000_000);
            }
        }

        return res;
    }

    @Override
    public List<String> generatePracticeWords(Letter letter, int len) {
        List<String> res = new LinkedList<>();

        List<String> allLetterWords = practiceWords.get(letter);
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            res.add(allLetterWords.get(random.nextInt(allLetterWords.size())));
        }

        return res;
    }
}
