package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Readability readability = new Readability(args[0]);

        displayCounts(readability);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL all): ");
        displayScores(readability, scanner.next());
    }

    public static void displayCounts(Readability readability) {
        System.out.printf("Words: %d\nSentences: %d\nCharacters: %d\nSyllables: %d\nPolysyllables: %d\n",
                readability.getWordCount(), readability.getSentenceCount(), readability.getCharacterCount(),
                readability.getSyllableCount(), readability.getPolysyllableCount());
    }

    public static void displayScores(Readability readability, String option) {
        int wordCount = readability.getWordCount();
        int sentenceCount = readability.getSentenceCount();
        int characterCount = readability.getCharacterCount();
        int syllableCount = readability.getSyllableCount();
        int polysyllableCount = readability.getPolysyllableCount();

        double ariScore = 4.71 * (1.0 * characterCount / wordCount) + 0.5 * (1.0 * wordCount / sentenceCount) - 21.43;
        double fkScore = 0.39 * (1.0 * wordCount / sentenceCount) + 11.8 * (1.0 * syllableCount / wordCount) - 15.59;
        double smogScore = 1.043 * Math.sqrt(polysyllableCount * (30.0 / sentenceCount)) + 3.1291;
        double clScore = 0.0588 * (1.0 * characterCount / wordCount * 100) - (0.296 * 1.0 * sentenceCount / wordCount * 100) - 15.8;

        System.out.println();

        switch (option) {
            case "ARI":
                System.out.println(Readability.getScore("Automated Readability Index", ariScore));
                break;
            case "FK":
                System.out.println(Readability.getScore("Flesch-Kincaid readability tests", fkScore));
                break;
            case "SMOG":
                System.out.println(Readability.getScore("Simple Measure of Gobbledygook", smogScore));
                break;
            case "CL":
                System.out.println(Readability.getScore("Coleman-Liau index", clScore));
                break;
            case "all":
                System.out.println(Readability.getScore("Automated Readability Index", ariScore));
                System.out.println(Readability.getScore("Flesch-Kincaid readability tests", fkScore));
                System.out.println(Readability.getScore("Simple Measure of Gobbledygook", smogScore));
                System.out.println(Readability.getScore("Coleman-Liau index", clScore));
                System.out.printf("This text should be understood on average by %.2f years old\n",
                        ((Readability.getAge(ariScore) + Readability.getAge(fkScore) +
                                Readability.getAge(smogScore) + Readability.getAge(clScore))) / 4.0);
                break;
        }
    }
}
