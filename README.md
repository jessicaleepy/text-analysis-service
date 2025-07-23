# Text Analysis Service

---

## 🧰 Java Version

- **Java:** 21.0.7
- **Maven:** 3.9.11

---
## 📖 Introduction
This Spring Boot application analyzes the text of Alice’s Adventures in Wonderland and computes a variety of statistics and linguistic insights, such as:

- Top 10 most frequent meaningful words (excluding stop words)

- Top 5 most common bigrams

- Longest words

- Palindromic words

- Most common word length

- Sentence with the highest lexical diversity

## 📥 Input
The analysis is performed on the full text of Alice’s Adventures in Wonderland, sourced from Project Gutenberg:

🔗 https://www.gutenberg.org/cache/epub/11/pg11.txt

## 🛠️ Build & Run Instructions

### ✅ Prerequisites

- Java 21 installed and `JAVA_HOME` set correctly
- Maven 3.9+ installed and available in your system `PATH`

### 🚀 Running the Application

1. Clone or download this project.

2. Open a terminal in the project root folder and run:

   ```bash
   mvn spring-boot:run

## 💡 Assumptions

### 🔤 Word Processing Assumptions

- Only words with two or more characters are considered in top words and palindrome analyses. Single-letter words are excluded from both.
- Words made up of repeating single characters (e.g. "iii", "www") are excluded in palindrome and top words analyses.
- Words are case-insensitive.
- Words containing digits (e.g., "1A1") are excluded.
- Stop words (common English words like "the", "and", "is") are ignored for top word calculations only.
- Hyphens (-) are treated as separators, so "non-profit" is split into "non" and "profit".
- Apostrophes (') within words are preserved (e.g. "don’t" remains "don’t").

### ✂️ Sentence Splitting Assumptions

- A sentence ends when a **punctuation mark** (`.`, `!`, `?`) is followed by:
    - whitespace and a capital letter
    - or whitespace + opening punctuation (`“`, `"`, `'`, `(`, `[`, `{`) + capital letter
- Quotes or parentheses after punctuation are not considered part of the sentence end.
- **Headings or non-sentence elements** (e.g., lines containing “Chapter”) are ignored.
- Sentences must have 5 or more words to be eligible for lexical diversity calculation.

### 📐 Lexical Diversity Rule

- Among all valid sentences, the one with the **highest ratio** is selected.
- If there is a **tie**, the **earliest** sentence (based on its position in the original text) will be chosen as the result.

## 🏗️ Design Decisions
### ⚙️ Application Architecture
- The application is built using Spring Boot, providing a lightweight and modular framework for rapid development.
- A central TextAnalyzeService coordinates the workflow by invoking each analysis module (e.g. top words, bigrams, palindromes), following the orchestration pattern.
- Each analysis is implemented as a dedicated @Service class, following the Single Responsibility Principle (SRP).
- Helper methods (e.g. text cleaning, sentence splitting) are encapsulated in a utility class and are primarily used within TextAnalyzeService before dispatching to other services.
- Responsibilities are cleanly separated to enhance maintainability, readability, and unit testing.
- Dependencies are managed using Spring’s dependency injection, enabling loose coupling and better testability.

### 🧵 Execution Strategy
- Initially considered parallel execution using CompletableFuture and a fixed thread pool for running individual analysis tasks.
- However, each task is CPU-bound and lightweight, making parallel execution less efficient due to:
  - Thread management overhead
  - Increased context switching
  - Lack of heavy I/O operations to benefit from concurrency
- After benchmarking, sequential execution proved faster and more consistent.
- Final implementation uses sequential execution for all analysis modules to optimize the performance.

### 🧼 Input Preprocessing
- Input text is normalized by:
  - Converting to lowercase. 
  - Replacing non-letter characters (except apostrophes) with spaces. 
  - Collapsing multiple whitespaces into single spaces.
- Sentences are identified using the following rules:
  - A sentence ends with `.`, `!`, or `?`, possibly followed by quotes or brackets (e.g. `"`, `)`, `]`).
  - A new sentence begins if the next character is:
    - An uppercase letter (e.g. `"Hello." She waved.`), or
    - An opening quote or bracket followed by an uppercase letter (e.g. `("Yes.")`).
  - Quotes are respected:
    - If a sentence-ending punctuation (like `.`, `!`, or `?`) appears inside an open quote, it is not treated as the end of a sentence unless it is immediately followed by a matching closing quote or bracket.
  
### 🔁 Tie-Breaking Rules
- Top 10 Words and Top 5 Bigrams:
  - Sorted first by frequency (descending).
  - Then by alphabetical order (ascending) to break ties.
- Longest Words:
  - If there are multiple longest words with the same length, they are sorted in alphabetical order (ascending)
- Most Common Word Length:
    - If multiple lengths have the same count, the shorter length is selected.
- Sentence with the Highest Lexical Diversity:
    - If multiple sentences have the same diversity score, the earliest one is chosen.

## 📦 Sample Output

### 📤 Output File
- After running the application, an output file named output.json will be generated.
- This file is written to the project root directory.

```json
{
  "topWords" : {
    "words" : [ "said", "alice", "little", "one", "gutenberg", "know", "project", "like", "went", "would" ],
    "durationMs" : 17
  },
  "topBigrams" : {
    "bigrams" : [ "said the", "of the", "said alice", "in a", "in the" ],
    "durationMs" : 22
  },
  "longestWords" : {
    "longestWords" : [ "unenforceability" ],
    "durationMs" : 4
  },
  "palindromes" : {
    "palindromicWords" : [ "pop", "eye", "tut", "non", "did", "wow", "ada" ],
    "durationMs" : 39
  },
  "wordLengthStats" : {
    "wordLength" : 3,
    "count" : 7676,
    "durationMs" : 6
  },
  "lexicalDiversity" : {
    "sentence" : "“Well!” thought Alice to herself, “after such a fall as this, I shall think nothing of tumbling down stairs!",
    "durationMs" : 38
  }
}