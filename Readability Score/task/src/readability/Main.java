package readability;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            String input = Files.readString(Path.of(args[0]));
            readScore(input);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void readScore(String input) {
        int characters = input.replaceAll("[\\s]+", "").length();
        int words = input.split("\\s+").length;
        int sentences = input.split("[!?.]").length;
        int syllables = 0;
        int polysyllables = 0;

        for (String word : input.replaceAll("e\\b|[.!?]", "").split(" ")) {
            int count = word.replaceAll("[^AEIOUYaeiouy]+", " ").trim().split(" ").length;
            syllables += count == 0 ? 1 : count;
            if (count > 2) {
                polysyllables++;
            }
        }

        double scoreARI = 4.71 * (characters/(double)words) + (0.5 * (words/(double)sentences) - 21.43);
        double scoreFK   = 0.39 * words / sentences + 11.8 * syllables / words - 15.59;
        double scoreSMOG = 1.043 * Math.sqrt(polysyllables * 30d / sentences) + 3.1291;
        double scoreCL   = 0.0588 * characters * 100d / words - 0.296 * sentences * 100d / words - 15.8;

        int[] ages = new int[] {6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24, 30};

        int ageARI  = ages[(int) Math.round(scoreARI)  - 1];
        int ageFK   = ages[(int) Math.round(scoreFK)   - 1];
        int ageSMOG = ages[(int) Math.round(scoreSMOG) - 1];
        int ageCL   = ages[(int) Math.round(scoreCL)   - 1];

        System.out.printf("The text is:%n%s%n%nWords: %d%nSentences: %d%nCharacters: %d%nSyllables: %d%nPolysyllables: %d%n",
                input, words, sentences, characters, syllables, polysyllables);

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String choice = new Scanner(System.in).next();
        System.out.println();

        switch (choice) {
            case "ARI":
                System.out.printf("Automated Readability Index: %.2f (about %d year olds).%n", scoreARI, ageARI);
                break;
            case "FK":
                System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d year olds).%n", scoreFK, ageFK);
                break;
            case "SMOG":
                System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d year olds).%n", scoreSMOG, ageSMOG);
                break;
            case "CL":
                System.out.printf("Coleman–Liau index: %.2f (about %d year olds).%n", scoreCL, ageCL);
                break;
            case "all":
                System.out.printf("Automated Readability Index: %.2f (about %d year olds).%n", scoreARI, ageARI);
                System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d year olds).%n", scoreFK, ageFK);
                System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d year olds).%n", scoreSMOG, ageSMOG);
                System.out.printf("Coleman–Liau index: %.2f (about %d year olds).%n", scoreCL, ageCL);
                break;
            default:
        }

        System.out.printf("%nThis text should be understood in average by %.2f year olds.",
                (ageARI + ageFK + ageSMOG + ageCL) / 4d);
    }
}