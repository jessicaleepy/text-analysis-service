package com.text.analysis.service;

import com.text.analysis.dto.BigramsResult;
import com.text.analysis.services.BigramService;
import com.text.analysis.utils.TextCleaner;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BigramServiceTest {
    private final BigramService bigramService = new BigramService();

    @Test
    void analyze_givenSimpleSentence_returnTopFiveResults() {
        String input = "Said Alice said the cat said Alice said Alice.";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        BigramsResult result = bigramService.analyze(cleanedWords);
        List<String> bigrams = result.bigrams();

        assertFalse(bigrams.isEmpty());
        assertEquals("said alice", bigrams.get(0));
        assertTrue(bigrams.contains("alice said"));
    }

    @Test
    void analyze_givenMoreThanFiveDistinctBigrams_thenReturnsOnlyTopFive_inAlphaOrder() {
        String input = "a b c d e f g h i j k l m n";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        BigramsResult result = bigramService.analyze(cleanedWords);
        assertEquals(5, result.bigrams().size());
        assertTrue(result.bigrams().contains("a b"));
        assertTrue(result.bigrams().contains("b c"));
        assertTrue(result.bigrams().contains("c d"));
        assertTrue(result.bigrams().contains("d e"));
        assertTrue(result.bigrams().contains("e f"));

    }

    @Test
    void analyze_whenCountsEqual_thenReturnTopFive_inAlphaOrder() {
        String input = "beta gamma alpha delta";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        BigramsResult result = bigramService.analyze(cleanedWords);
        List<String> bigrams = result.bigrams();

        assertEquals(3, bigrams.size());
        assertEquals(List.of("alpha delta", "beta gamma", "gamma alpha"), bigrams);
    }

    @Test
    void analyze_givenBigramsContainingDigits_thenReturnResultWithoutDigits() {
        String input = "hello 123 world 9 lives hello world";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        BigramsResult result = bigramService.analyze(cleanedWords);
        List<String> bigrams = result.bigrams();

        assertTrue(bigrams.contains("hello world"));
        assertTrue(bigrams.contains("lives hello"));
        assertFalse(bigrams.stream().anyMatch(b -> b.chars().anyMatch(Character::isDigit)));
    }

    @Test
    void analyze_givenWordsWithApostrophes_thenReturnThem() {
        String input = "don't stop don't move";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        BigramsResult result = bigramService.analyze(cleanedWords);
        List<String> bigrams = result.bigrams();

        assertEquals(3, bigrams.size());
        assertTrue(bigrams.contains("don't stop"));
        assertTrue(bigrams.contains("stop don't"));
        assertTrue(bigrams.contains("don't move"));
    }

    @Test
    void analyze_givenWordsWithPunctuation_thenReturnCorrectly() {
        String input = "Hello, world! Hello... world?";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        BigramsResult result = bigramService.analyze(cleanedWords);
        List<String> bigrams = result.bigrams();

        assertEquals(2, bigrams.size());
        assertEquals("hello world", bigrams.get(0));
        assertTrue(bigrams.contains("world hello"));
    }
}
