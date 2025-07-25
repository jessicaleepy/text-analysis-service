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
        boolean inQuote = false;

        int i = 0;
        while (i < cleaned.length()) {
            char c = cleaned.charAt(i);
            current.append(c);

            if (isOpeningPunctuation(c)) {
                inQuote = true;
            }

            // Look for end punctuation
            if (c == '.' || c == '!' || c == '?') {
                int j = i + 1;

                if (j < cleaned.length() && inQuote) {
                    if(isClosingPunctuation(cleaned.charAt(j))) {
                        inQuote = false;
                    }
                    else {
                        i++;
                        continue;
                    }
                }
                // Include trailing quotes or brackets after the punctuation
                while (j < cleaned.length() && isTrailingPunctuation(cleaned.charAt(j))) {
                    current.append(cleaned.charAt(j));
                    j++;
                }

                // Determine if a new sentence follows:
                // Case 1: space + quote/bracket + uppercase
                // Case 2: space + uppercase
                // Case 3: end of input

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

        // Add the last sentence (i.e. the end of input) if anything left
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

    private static boolean isClosingPunctuation(char c) {
        return c == '"' || c == '\'' || c == '”' || c == ')' || c == ']' || c == '}';
    }
}
