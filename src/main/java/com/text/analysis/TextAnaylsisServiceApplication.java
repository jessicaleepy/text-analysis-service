package com.text.analysis;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.text.analysis.dto.TextAnalysisResult;
import com.text.analysis.utils.FileLoader;
import com.text.analysis.services.TextAnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class TextAnaylsisServiceApplication implements CommandLineRunner {

	@Autowired
	private TextAnalyzeService analyzeService;

	public static void main(String[] args) {
		SpringApplication.run(TextAnaylsisServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
		String input = FileLoader.loadFile("input.txt");

		TextAnalysisResult result = analyzeService.analyze(input);

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writerWithDefaultPrettyPrinter()
					.writeValue(new File("output.json"), result);
			System.out.println("Analysis complete: output.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
