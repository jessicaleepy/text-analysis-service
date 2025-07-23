package com.text.analysis.service;

import com.text.analysis.dto.TopWordsResult;
import com.text.analysis.services.TopWordsService;
import com.text.analysis.utils.TextCleaner;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TopWordsServiceTest {
    private final TopWordsService topWordsService = new TopWordsService();

    @Test
    void analyze_givenSimpleInput_whenStopwordsPresent_thenCountsNonStopWords() {
        String input = "I said you are nice. I said you are smart. I said I appreciate you.";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);
        TopWordsResult result = topWordsService.analyze(cleanedWords);
        List<String> words = result.words();

        assertTrue(words.containsAll(List.of("said", "nice", "smart", "appreciate")));
        assertFalse(words.contains("you"));
        assertEquals(4, words.size());
        assertEquals("said", words.get(0));
    }

    @Test
    void analyze_givenMoreThanTenDistinctWords_thenReturnsExactlyTopTen() {
        StringBuilder sb = new StringBuilder();
        sb.append("alice ".repeat(30));
        sb.append("rabbit ".repeat(20));
        sb.append("cat ".repeat(15));
        sb.append("mouse ".repeat(12));
        sb.append("queen ".repeat(10));
        sb.append("king ".repeat(9));
        sb.append("hatter ".repeat(8));
        sb.append("time ".repeat(7));
        sb.append("tea ".repeat(6));
        sb.append("hole ".repeat(5));
        sb.append("door ".repeat(4));
        sb.append("key ".repeat(3));
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(sb.toString());

        TopWordsResult result = topWordsService.analyze(cleanedWords);
        List<String> top = result.words();

        assertEquals(10, top.size());
        assertEquals("alice", top.get(0));
        assertTrue(top.contains("rabbit"));
        assertFalse(top.contains("key"));
    }

    @Test
    void analyze_givenWordsWithSingleCharacter_thenExcludeThem() {
        String[] input = { "a", "b", "c", "hello", "world", "a" };
        TopWordsResult result = topWordsService.analyze(input);

        List<String> words = result.words();
        assertFalse(words.contains("a"));
        assertFalse(words.contains("b"));
        assertFalse(words.contains("c"));
        assertTrue(words.contains("hello"));
        assertTrue(words.contains("world"));
    }

    @Test
    void analyze_givenSingleRepeatedCharacterWords_thenExcludeThem() {
        String[] input = { "ii", "aaa", "www", "wow", "book" };
        TopWordsResult result = topWordsService.analyze(input);

        List<String> words = result.words();
        assertFalse(words.contains("ii"));
        assertFalse(words.contains("aaa"));
        assertFalse(words.contains("www"));
        assertTrue(words.contains("wow"));
        assertTrue(words.contains("book"));
    }

    @Test
    void analyze_givenOnlyStopwords_thenReturnsEmptyList() {
        String input = "the and or but if we you they she he it";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        TopWordsResult r = topWordsService.analyze(cleanedWords);
        assertTrue(r.words().isEmpty());
    }

    @Test
    void analyze_givenPureDigits_thenIgnoresWordsWithNumbersOnly() {
        String input = "code 123 456 code 789 123 code";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        TopWordsResult r = topWordsService.analyze(cleanedWords);
        List<String> words = r.words();
        assertEquals("code", words.get(0));
        assertFalse(words.contains("123"));
        assertFalse(words.contains("456"));
        assertFalse(words.contains("789"));

    }
}
