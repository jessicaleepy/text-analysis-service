package com.text.analysis.service;

import com.text.analysis.dto.WordLengthResult;
import com.text.analysis.services.WordLengthCountService;
import com.text.analysis.utils.TextCleaner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordLengthCountServiceTest {
    private final WordLengthCountService wordLengthCountService = new WordLengthCountService();

    @Test
    void analyze_givenSimpleInput_thenReturnMostFrequentLength() {
        String input = "hi go ok word";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        WordLengthResult result = wordLengthCountService.analyze(cleanedWords);

        assertEquals(2, result.wordLength());
        assertEquals(3, result.count());
    }

    @Test
    void analyze_givenLargeInput_thenReturnMostFrequentLength() {
        String input = "abcde ".repeat(100) + "cat ".repeat(80);
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        WordLengthResult result = wordLengthCountService.analyze(cleanedWords);

        assertEquals(5, result.wordLength());
        assertEquals(100, result.count());
    }

    @Test
    void analyze_givenTieOnFrequency_whenEqualCounts_thenReturnShorterLength() {
        String input = "hi cat";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        WordLengthResult result = wordLengthCountService.analyze(cleanedWords);

        assertEquals(2, result.wordLength());
        assertEquals(1, result.count());
    }

    @Test
    void analyze_givenWordsWithDigits_whenFiltered_thenIgnoreThoseWords() {
        String input = "ab3 123 foo";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        WordLengthResult result = wordLengthCountService.analyze(cleanedWords);

        assertEquals(3, result.wordLength());
        assertEquals(1, result.count());
    }

    @Test
    void analyze_givenSingleWord_thenReturnItsLengthAndCountOne() {
        String input = "hippopotamus";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        WordLengthResult result = wordLengthCountService.analyze(cleanedWords);

        assertEquals(12, result.wordLength());
        assertEquals(1, result.count());
    }

    @Test
    void analyze_givenWordsWithApostrophes_whenKept_thenReturnCorrectResult() {
        String input = "don't cant can't";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        WordLengthResult result = wordLengthCountService.analyze(cleanedWords);

        assertEquals(5, result.wordLength());
        assertEquals(2, result.count());
    }
}
