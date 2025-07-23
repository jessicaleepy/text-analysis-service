package com.text.analysis.services;

import com.text.analysis.dto.BigramsResult;
import com.text.analysis.utils.TextCleaner;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BigramService {
    public BigramsResult analyze(String[] cleaned) {
        long start = System.currentTimeMillis();

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < cleaned.length - 1; i++) {
            String w1 = cleaned[i], w2 = cleaned[i + 1];
            if (TextCleaner.hasDigit(w1) || TextCleaner.hasDigit(w2)) continue;
            String bigram = w1 + " " + w2;
            map.merge(bigram, 1, Integer::sum);
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((a,b) -> {
            if(a.getValue().equals(b.getValue())) {
                return a.getKey().compareTo(b.getKey());
            } else {
                return Integer.compare(b.getValue(), a.getValue());
            }
        });

        List<String> result = list.stream()
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
        long end = System.currentTimeMillis();
        return new BigramsResult(result, end - start);
    }
}
