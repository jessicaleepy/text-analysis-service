package com.text.analysis.service;

import com.text.analysis.dto.LexicalDiversityResult;
import com.text.analysis.services.LexicalDiversityService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexicalDiversityServiceTest {
    private final LexicalDiversityService lexicalDiversityService = new LexicalDiversityService();

    @Test
    void analyze_givenMultipleSentences_returnsSentenceWithHighestDiversity() {
        String s1 = "Blue blue cars race fast fast every day.";
        String s2 = "Quantum mechanics puzzles curious minds daily!";
        String input = s1 + " " + s2;

        LexicalDiversityResult r = lexicalDiversityService.analyze(input);
        assertEquals(s2, r.sentence());
    }


    @Test
    void analyze_givenInvalidOrShortSentences_returnsOnlyValidSentence() {
        String s1 = "Short and boring"; // no '.', '!', '?'
        String s2 = "Tiny!";            // less than 5 words
        String s3 = "Legitimate sentences should actually end with punctuation.";
        LexicalDiversityResult r = lexicalDiversityService.analyze(s1 + " " + s2 + " " + s3);
        assertEquals(s3, r.sentence());
    }

    @Test
    void analyze_givenTrailingQuotesOrParens_returnsFirstEncounteredSentence() {
        String s1 = "He whispered, \"try to be brave!\""; // ends with !"
        String s2 = "They responded us (of course).";     // ends with .)
        LexicalDiversityResult r = lexicalDiversityService.analyze(s1 + " " + s2);
        assertEquals(s1, r.sentence());
    }

    @Test
    void analyze_givenTieInDiversity_returnsFirstEncounteredSentence() {
        String s1 = "Alpha beta gamma delta epsilon!";
        String s2 = "Zebra yak xerus wombat quokka!";
        LexicalDiversityResult r = lexicalDiversityService.analyze(s1 + " " + s2);
        assertEquals(s1, r.sentence());
    }

    @Test
    void analyze_givenHeadingStyleSentence_returnsNonHeadingSentence() {
        String heading = "CHAPTER IV. Introduction to Widgets.";
        String body   = "Widgets empower developers to prototype ideas remarkably quickly!";
        LexicalDiversityResult r = lexicalDiversityService.analyze(heading + " " + body);
        assertEquals(body, r.sentence());
    }

}
