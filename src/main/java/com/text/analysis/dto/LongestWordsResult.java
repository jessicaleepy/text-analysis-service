package com.text.analysis.dto;

import java.util.List;

public record LongestWordsResult(List<String> words, long durationMs) {}