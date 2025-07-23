package com.text.analysis.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileLoader {
    public static String loadFile(String fileName) throws IOException {
        InputStream is = FileLoader.class.getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            throw new IOException("Resource not found: " + fileName);
        }
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }
}
