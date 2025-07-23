package com.text.analysis.dto;

import java.util.Set;

public record PalindromesResult(Set<String> palindromicWords, long durationMs) {}
