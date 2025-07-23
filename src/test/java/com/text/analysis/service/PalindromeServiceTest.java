package com.text.analysis.service;

import com.text.analysis.dto.PalindromesResult;
import com.text.analysis.services.PalindromeService;
import com.text.analysis.utils.TextCleaner;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PalindromeServiceTest {
    private final PalindromeService palindromeService = new PalindromeService();

    @Test
    void analyze_givenValidPalindromes_thenReturnResult() {
        String input = "level deed noon cat dog";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);
        PalindromesResult result = palindromeService.analyze(cleanedWords);
        assertEquals(Set.of("level", "deed", "noon"), result.palindromicWords());
    }

    @Test
    void analyze_givenWordsWithSingleCharacter_thenReturnEmpty() {
        String input = "a b c";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        PalindromesResult result = palindromeService.analyze(cleanedWords);
        assertTrue(result.palindromicWords().isEmpty());
    }

    @Test
    void analyze_givenWordsWithAllSameCharacters_thenReturnEmpty() {
        String input = "aaa bbb cc dd";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        PalindromesResult result = palindromeService.analyze(cleanedWords);
        assertTrue(result.palindromicWords().isEmpty());
    }

    @Test
    void analyze_givenTokensWithNonLetters_thenReturnEmpty() {
        String input = "ab1ba b2ab";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        PalindromesResult result = palindromeService.analyze(cleanedWords);
        assertTrue(result.palindromicWords().isEmpty());
    }

    @Test
    void analyze_givenDuplicatePalindrome_thenReturnResultWithoutDuplicate() {
        String input = "level level level noon noon";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        PalindromesResult result = palindromeService.analyze(cleanedWords);
        assertEquals(Set.of("level", "noon"), result.palindromicWords());
    }

    @Test
    void analyze_givenWordsWithUpperCase_thenReturnResultWithLowerCase() {
        String input = "RaceCar Madam";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        PalindromesResult result = palindromeService.analyze(cleanedWords);
        assertTrue(result.palindromicWords().containsAll(Set.of("racecar", "madam")));
    }

    @Test
    void analyze_givenInputWithInvalidWords_andValidPalindrome_thenReturnCorrectly() {
        String input = "did eye pop ''' 12321 ab1ba noon";
        String[] cleanedWords = TextCleaner.cleanAndSplitWords(input);

        PalindromesResult result = palindromeService.analyze(cleanedWords);
        assertEquals(Set.of("did", "eye", "pop", "noon"), result.palindromicWords());
    }
}
