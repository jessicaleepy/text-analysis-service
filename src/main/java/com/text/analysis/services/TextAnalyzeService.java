package com.text.analysis.services;

import com.text.analysis.dto.*;
import com.text.analysis.utils.TextCleaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextAnalyzeService {
    private final TopWordsService topWordsService;
    private final BigramService bigramService;
    private final LongestWordService longestWordService;
    private final PalindromeService palindromeService;
    private final WordLengthCountService wordLengthCountService;
    private final LexicalDiversityService lexicalDiversityService;

    @Autowired
    public TextAnalyzeService(TopWordsService topWordsService,
                              BigramService bigramService, LongestWordService longestWordService, PalindromeService palindromeService, WordLengthCountService wordLengthCountService, LexicalDiversityService lexicalDiversityService) {
        this.topWordsService = topWordsService;
        this.bigramService = bigramService;
        this.longestWordService = longestWordService;
        this.palindromeService = palindromeService;
        this.wordLengthCountService = wordLengthCountService;
        this.lexicalDiversityService = lexicalDiversityService;
    }

    public TextAnalysisResult analyze(String text) {
        String[] cleaned = TextCleaner.cleanAndSplitWords(text);

        TopWordsResult topWords = topWordsService.analyze(cleaned);
        BigramsResult bigrams = bigramService.analyze(cleaned);
        LongestWordsResult longestWords = longestWordService.analyze(cleaned);
        PalindromesResult palindromes = palindromeService.analyze(cleaned);
        WordLengthResult wordLengthStats = wordLengthCountService.analyze(cleaned);
        LexicalDiversityResult lexicalDiversity = lexicalDiversityService.analyze(text);

        return new TextAnalysisResult(
                topWords,
                bigrams,
                longestWords,
                palindromes,
                wordLengthStats,
                lexicalDiversity
        );
    }
}
