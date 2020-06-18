package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Readability {
    private final int characterCount;
    private final int wordCount;
    private final int sentenceCount;
    private int syllableCount;
    private int polysyllableCount;

    public Readability(String filePath) {
        File file = new File(filePath);
        String fileContents = "";

        try (Scanner scanner = new Scanner(file)) {
            fileContents = scanner.nextLine();
        } catch (FileNotFoundException e) {
            System.out.printf("File not found: %s\n", filePath);
        }

        System.out.printf("The text is:\n%s\n\n", fileContents);

        this.characterCount = fileContents.replace(" ", "").length();
        this.wordCount = fileContents.split("\\s").length;
        this.sentenceCount = fileContents.split("[!?.]").length;
        this.syllableCount = 0;
        this.polysyllableCount = 0;

        for (String word : fileContents.toLowerCase().replaceAll("[!?,.]", "").split("\\s")) {
            this.syllableCount += countSyllables(word);
            if (countSyllables(word) > 2) {
                this.polysyllableCount++;
            }
        }
        

    }

    private int countSyllables(String word) {
        int vowelCount = 0;

        if (word.equals("you")) {
            return 1;
        }

        for (int i = 0; i < word.length(); i++) {
            if (i == word.length() - 1 && word.charAt(i) == 'e') {
                continue;
            }

            if (i + 1 < word.length() && word.substring(i, i + 2).matches("[aeiouy]{2}")) {
                vowelCount++;
                i++;
                continue;
            }

            if (("" + word.charAt(i)).matches("[aeiouy]")) {
                vowelCount++;
            }
        }

        if (vowelCount == 0) {
            return 1;
        }

        return vowelCount;
    }

    public static String getScore(String testName, double scoreCalculation) {
        return String.format("%s: %.2f (about %d years old).", testName, scoreCalculation, getAge(scoreCalculation));
    }

    public static int getAge(double score) {
        return new int[] {6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24}
                        [(int) (Math.max(1, Math.min(Math.round(score), 13)) - 1)];
    }

    public int getWordCount() {
        return wordCount;
    }

    public int getCharacterCount() {
        return characterCount;
    }

    public int getSentenceCount() {
        return sentenceCount;
    }

    public int getSyllableCount() {
        return syllableCount;
    }

    public int getPolysyllableCount() {
        return polysyllableCount;
    }
}
