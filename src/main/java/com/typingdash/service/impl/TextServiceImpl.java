package com.typingdash.service.impl;

import com.typingdash.enums.Difficulty;
import com.typingdash.enums.Language;
import com.typingdash.enums.Letter;
import com.typingdash.service.TextService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

@Service
public class TextServiceImpl implements TextService {
    private final HashMap<Language, Map<Difficulty, LinkedList<String>>> words = new HashMap<>();
    private final Map<Language, Map<Difficulty, LinkedList<String>>> sentences = new HashMap<>();
    private final Map<Letter, List<String>> practiceWords = new HashMap<>();

    public TextServiceImpl() throws IOException {
        initializeWords();
        initializeSentences();
        initializePracticeWords();
    }


    private void initializeWords() throws IOException {
        addWordsSet(Language.CHINESE, Path.of("src/main/resources/words/chinese"));
        addWordsSet(Language.ENGLISH, Path.of("src/main/resources/words/english"));
        addWordsSet(Language.GERMAN, Path.of("src/main/resources/words/german"));
        addWordsSet(Language.POLISH, Path.of("src/main/resources/words/polish"));
        addWordsSet(Language.RUSSIAN, Path.of("src/main/resources/words/russian"));
        addWordsSet(Language.SPANISH, Path.of("src/main/resources/words/spanish"));
    }

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

        this.words.put(language, words);
    }


    private void initializeSentences() throws IOException {
        addSentencesSet(Language.CHINESE, Path.of("src/main/resources/sentences/chinese"));
        addSentencesSet(Language.ENGLISH, Path.of("src/main/resources/sentences/english"));
        addSentencesSet(Language.GERMAN, Path.of("src/main/resources/sentences/german"));
        addSentencesSet(Language.POLISH, Path.of("src/main/resources/sentences/polish"));
        addSentencesSet(Language.RUSSIAN, Path.of("src/main/resources/sentences/russian"));
        addSentencesSet(Language.SPANISH, Path.of("src/main/resources/sentences/spanish"));
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

        this.sentences.put(language, sentences);
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
    public List<String> generateWords(Language lang, Difficulty difficulty, int len) {
        List res = new LinkedList();

        List<String> textWords = words.get(lang).get(difficulty);
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            res.add(textWords.get(random.nextInt(textWords.size())));
        }

        return res;
    }

    @Override
    public List<String> generateSentences(Language lang, Difficulty difficulty, int len) {
        List res = new LinkedList();

        LinkedList<String> textSentences = sentences.get(lang).get(difficulty);
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            res.add(textSentences.get(random.nextInt(textSentences.size())));
        }

        return res;
    }


    @Override
    public String generateTopicWords(Language lang, Difficulty difficulty, int len, String topic) { //todo
        return null;
    }

    @Override
    public String generateTopicSentences(Language lang, Difficulty difficulty, int len, String topic) { //todo
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
