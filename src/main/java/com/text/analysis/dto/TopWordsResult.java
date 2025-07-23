package com.text.analysis.dto;

import java.util.List;

public record TopWordsResult(List<String> words, long durationMs) {}
