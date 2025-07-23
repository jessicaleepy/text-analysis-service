package com.text.analysis.dto;

import java.util.List;

public record BigramsResult (List<String> bigrams, long durationMs) {}

