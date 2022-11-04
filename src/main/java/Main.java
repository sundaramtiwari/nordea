import entity.Sentence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import writer.CSVFileWriter;

public class Main {

    private static final List<String> headers = new ArrayList<>();
    private static final Set<Character> SENTENCE_TERMINATORS = new HashSet<>();
    private static final Set<Character> WORD_JOINERS = new HashSet<>();
    private static final Set<String> ABBREVIATIONS = new HashSet<>();

    static {
        WORD_JOINERS.add('\'');
        WORD_JOINERS.add('’');

        SENTENCE_TERMINATORS.add('.');
        SENTENCE_TERMINATORS.add('!');
        SENTENCE_TERMINATORS.add('?');

        ABBREVIATIONS.add("MR");
        ABBREVIATIONS.add("DR");
        ABBREVIATIONS.add("MRS");
        ABBREVIATIONS.add("MS");
        ABBREVIATIONS.add("Mr");
        ABBREVIATIONS.add("Dr");
        ABBREVIATIONS.add("Mrs");
        ABBREVIATIONS.add("Engr");
        ABBREVIATIONS.add("ENGR");
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        List<Sentence> sentences = new ArrayList<>();
        List<String> words = new ArrayList<>();
        StringBuilder sbr = new StringBuilder();
        int numOfColumns = 0;

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                if (Character.isLetterOrDigit(ch) || WORD_JOINERS.contains(ch)) {
                    sbr.append(ch);

                } else if (SENTENCE_TERMINATORS.contains(ch)) {
                    if ('.' == ch && ABBREVIATIONS.contains(sbr.toString())) {
                        sbr.append(ch);
                        words.add(sbr.toString());
                        sbr = new StringBuilder();
                        continue;
                    }

                    if (!sbr.isEmpty()) {
                        words.add(sbr.toString());
                        sbr = new StringBuilder();
                    }

                    if (!words.isEmpty()) {
                        numOfColumns = Math.max(words.size(), numOfColumns);
                        words.sort(String::compareToIgnoreCase);
                        words.add(0, "Sentence " + (sentences.size() + 1));
                        sentences.add(new Sentence(words));
                        words = new ArrayList<>();
                    }
                } else if (!sbr.isEmpty()) {
                    words.add(sbr.toString());
                    sbr = new StringBuilder();
                }
            }
        }
        headers.add("");
        for (int i = 1; i <= numOfColumns; i++) {
            headers.add("Word " + i);
        }

        String fileName = "src/main/java/output.csv";
        CSVFileWriter fileWriter = new CSVFileWriter(fileName, headers.toArray(String[]::new));
        fileWriter.writeToFile(sentences);
    }
}