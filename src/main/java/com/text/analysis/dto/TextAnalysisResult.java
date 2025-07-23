package com.text.analysis.dto;

public record TextAnalysisResult(
        TopWordsResult topTenWords,
        BigramsResult topFiveBigrams,
        LongestWordsResult longestWords,
        PalindromesResult palindromes,
        WordLengthResult wordLengthStats,
        LexicalDiversityResult sentenceWithHighestLexicalDiversity
) {}