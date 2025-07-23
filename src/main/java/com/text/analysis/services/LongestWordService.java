package com.text.analysis.services;

import com.text.analysis.dto.LongestWordsResult;
import com.text.analysis.utils.TextCleaner;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LongestWordService {
    public LongestWordsResult analyze(String[] cleaned) {
        long start = System.currentTimeMillis();

        Set<String> uniqueWords = new HashSet<>();
        int maxLen = 0;
        List<String> longestWords = new ArrayList<>();

        for(String word : cleaned) {
            if(word.isEmpty() || TextCleaner.hasDigit(word)) continue;
            if(!uniqueWords.add(word)) continue;
            int len = word.length();
            if (len > maxLen) {
                maxLen = len;
                longestWords.clear();
                longestWords.add(word);
            } else if (len == maxLen) {
                longestWords.add(word);
            }
        }
        longestWords.sort(String::compareTo);
        long end = System.currentTimeMillis();

        return new LongestWordsResult(longestWords, end - start);
    }
}
