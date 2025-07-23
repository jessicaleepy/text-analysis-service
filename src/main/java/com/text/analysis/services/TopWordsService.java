package com.text.analysis.services;

import com.text.analysis.dto.TopWordsResult;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopWordsService {
    public static final Set<String> stopWords = Set.of(
            "i", "me", "my", "myself", "we", "our", "ours", "ourselves",
            "you", "your", "yours", "yourself", "yourselves",
            "he", "him", "his", "himself", "she", "her", "hers", "herself",
            "it", "its", "itself",
            "they", "them", "their", "theirs", "themselves",
            "what", "which", "who", "whom", "this", "that", "these", "those",
            "am", "is", "are", "was", "were", "be", "been", "being",
            "have", "has", "had", "having",
            "do", "does", "did", "doing",
            "a", "an", "the",
            "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against",
            "between", "into", "through", "during", "before", "after",
            "above", "below", "to", "from", "up", "down", "in", "out",
            "on", "off", "over", "under",
            "again", "further", "then", "once",
            "here", "there", "when", "where", "why", "how",
            "all", "any", "both", "each", "few", "more", "most",
            "other", "some", "such",
            "no", "nor", "not", "only", "own", "same", "so",
            "than", "too", "very",
            "s", "t", "can", "will", "just", "don", "should", "now",
            "d", "ll", "m", "o", "re", "ve", "y",
            "ain", "aren", "couldn", "didn", "doesn", "hadn", "hasn",
            "haven", "isn", "ma", "mightn", "mustn", "needn",
            "shan", "shouldn", "wasn", "weren", "won", "wouldn"
    );

    public TopWordsResult analyze(String[] cleaned) {
        long start = System.currentTimeMillis();

        Map<String, Integer> map = new HashMap<>();
        for(String word : cleaned) {
            if(word.isEmpty() || stopWords.contains(word) || word.chars().anyMatch(Character::isDigit)) continue;
            map.merge(word, 1, Integer::sum);
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
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();
        long end = System.currentTimeMillis();

        return new TopWordsResult(result, end - start);
    }
}
