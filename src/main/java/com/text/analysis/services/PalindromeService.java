package com.text.analysis.services;

import com.text.analysis.dto.PalindromesResult;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PalindromeService {
    public PalindromesResult analyze(String[] cleaned) {
        long start = System.currentTimeMillis();

        Set<String> result = new HashSet<>();
        for(String word : cleaned) {
            if (word.length() < 2) continue;
            boolean singleChar = word.chars().distinct().limit(2).count() == 1;
            if (singleChar) continue;

            boolean onlyLetters = word.chars().allMatch(c -> (c >= 'a' && c <= 'z'));
            if(result.contains(word) || !onlyLetters) continue;
            if(isPalindromic(word)) result.add(word);
        }
        long end = System.currentTimeMillis();

        return new PalindromesResult(result, end - start);
    }

    private static boolean isPalindromic(String word) {
        int left = 0;
        int right = word.length() - 1;
        boolean isPalindromic = true;
        while(left <= right) {
            if(word.charAt(left) != word.charAt(right)) {
                isPalindromic = false;
                break;
            }
            left++;
            right--;
        }
        return isPalindromic;
    }
}
