package com.text.analysis.service;

import com.text.analysis.dto.LongestWordsResult;
import com.text.analysis.services.LongestWordService;
import com.text.analysis.utils.TextCleaner;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LongestWordsServiceTest {
    private final LongestWordService longestWordService = new LongestWordService();

    @Test
    void analyze_givenSingleLongestWord_thenReturnThatWord() {
        String input = "cat hippopotamus dog";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        LongestWordsResult result = longestWordService.analyze(cleanedWords);
        assertEquals(List.of("hippopotamus"), result.words());
    }

    @Test
    void analyze_givenTieForMaxLength_thenReturnResult_sortedAlphabetically() {
        String input = "alpha beta gamma delta zzzzzz zzzzza";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        LongestWordsResult result = longestWordService.analyze(cleanedWords);

        assertEquals(List.of("zzzzza", "zzzzzz"), result.words());
        assertEquals("zzzzza", result.words().get(0));
    }

    @Test
    void analyze_givenDuplicates_thenReturnResultWithoutDuplicates() {
        String input = "pneumonoultramicroscopicsilicovolcanoconiosis pneumonoultramicroscopicsilicovolcanoconiosis short short";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        LongestWordsResult r = longestWordService.analyze(cleanedWords);
        assertEquals(1, r.words().size());
        assertEquals("pneumonoultramicroscopicsilicovolcanoconiosis", r.words().get(0));
    }

    @Test
    void analyze_givenWordsWithApostrophes_thenReturnResult_sortedAlphabetically() {
        String input = "don't can't can";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        LongestWordsResult result = longestWordService.analyze(cleanedWords);
        assertEquals(2, result.words().size());

        assertEquals(List.of("can't", "don't"), result.words());
    }

    @Test
    void analyze_givenWordsWithDigits_thenReturnResultWithNoDigit() {
        String input = "abc12345 xyz 1234567";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        LongestWordsResult result = longestWordService.analyze(cleanedWords);
        assertEquals(List.of("xyz"), result.words());
    }

    @Test
    void analyze_whenPunctuationPresent_thenReturnWordsThatAreNotMerged() {
        String input = "hello,world!!! super-long-word";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        LongestWordsResult result = longestWordService.analyze(cleanedWords);
        assertEquals(List.of("hello", "super", "world"), result.words());
    }
}
