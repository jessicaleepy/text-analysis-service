package com.text.analysis.services;

import com.text.analysis.dto.WordLengthResult;
import com.text.analysis.utils.TextCleaner;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WordLengthCountService {
    public WordLengthResult analyze(String[] cleaned) {
        long start = System.currentTimeMillis();

        Map<Integer, Integer> map = new HashMap<>();
        for(String word : cleaned) {
            if(word.isEmpty() || TextCleaner.hasDigit(word)) continue;
            map.merge(word.length(), 1, Integer::sum);
        }

        Map.Entry<Integer,Integer> best = null;
        for (Map.Entry<Integer,Integer> e : map.entrySet()) {
            if (best == null || e.getValue() > best.getValue()
                    || (e.getValue().equals(best.getValue()) && e.getKey() < best.getKey())) {
                best = e;
            }
        }
        long end = System.currentTimeMillis();

        return new WordLengthResult(best.getKey(), best.getValue(), end - start);
    }
}
