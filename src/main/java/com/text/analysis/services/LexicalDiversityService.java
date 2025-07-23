package com.text.analysis.services;

import com.text.analysis.dto.LexicalDiversityResult;
import com.text.analysis.utils.TextCleaner;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class LexicalDiversityService {
    public LexicalDiversityResult analyze(String input) {
        long start = System.currentTimeMillis();
        String[] sentence = TextCleaner.cleanSentences(input); // split by tabs/newline/spaces which are right after a punctuation
        String result = "";
        double diversity = -1.0;

        for(String sen : sentence) {
            String trimmed = sen.trim();

            if (!isValidSentence(trimmed)) continue;

            String[] words = TextCleaner.cleanAndSplitWords(trimmed);
            if (words.length < 5) continue;

            Set<String> uniqueWords = new HashSet<>();
            int totalWords = 0;
            for(String word : words) {
                if(word.isEmpty()) continue;
                uniqueWords.add(word);
                totalWords++;
            }
            if(totalWords == 0) continue;

            double currentDiversity = (double) uniqueWords.size() / totalWords;
            if(currentDiversity > diversity) {
                result = trimmed;
                diversity = currentDiversity;
            }
        }
        long end = System.currentTimeMillis();

        return new LexicalDiversityResult(result, end - start);
    }

    private boolean isValidSentence(String s) {
        if (s == null || s.isEmpty()) return false;
        String TRAIL = "\"'”’)]}";

        // require end punctuation (ignore trailing quotes/parens)
        int i = s.length() - 1;
        while (i >= 0 && TRAIL.indexOf(s.charAt(i)) >= 0) i--;
        if (i < 0) return false;    // only quotes, no content

        char last = s.charAt(i);
        if (last != '.' && last != '!' && last != '?') return false;

        return !s.toLowerCase().contains("chapter ");
    }
}
