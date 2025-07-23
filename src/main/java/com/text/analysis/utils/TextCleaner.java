package com.text.analysis.utils;

import java.util.ArrayList;
import java.util.List;

public class TextCleaner {
    public static boolean hasDigit(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) return true;
        }
        return false;
    }

    public static String[] cleanAndSplitWords(String input) {
        return input.toLowerCase().replaceAll("[^a-z0-9'\\s]", " ").trim().split("\\s+");
    }

    public static String[] cleanSentences(String input) {
        List<String> sentences = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        String cleaned = input.replace('\r', ' ').replace('\n', ' ').replaceAll("\\s+", " ").trim();

        int i = 0;
        while (i < cleaned.length()) {
            char c = cleaned.charAt(i);
            current.append(c);

            // Look for end punctuation
            if (c == '.' || c == '!' || c == '?') {
                int j = i + 1;

                // Skip any trailing quotes or brackets
                while (j < cleaned.length() && isTrailingPunctuation(cleaned.charAt(j))) {
                    current.append(cleaned.charAt(j));
                    j++;
                }

                // Check for space + uppercase letter (new sentence)
                if (
                        (j < cleaned.length() - 2 &&
                                cleaned.charAt(j) == ' ' &&
                                isOpeningPunctuation(cleaned.charAt(j + 1)) &&
                                Character.isUpperCase(cleaned.charAt(j + 2)))
                                ||
                                (j < cleaned.length() - 1 &&
                                        cleaned.charAt(j) == ' ' &&
                                        Character.isUpperCase(cleaned.charAt(j + 1)))
                                ||
                                j >= cleaned.length()
                ) {
                    sentences.add(current.toString().trim());
                    current.setLength(0);
                    i = j;
                    continue; // skip i++ to avoid double increment
                }
            }

            i++;
        }

        // Add the last sentence if anything left
        if (current.length() > 0) {
            sentences.add(current.toString().trim());
        }

        return sentences.toArray(new String[0]);
    }

    private static boolean isTrailingPunctuation(char c) {
        return c == '"' || c == '\'' || c == ')' || c == ']' || c == '}';
    }
    private static boolean isOpeningPunctuation(char c) {
        return c == '"' || c == '\'' || c == '“' || c == '(' || c == '[' || c == '{';
    }
}
