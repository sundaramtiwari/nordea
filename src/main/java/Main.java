import entity.Sentence;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import writer.CSVFileWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static List<String> headers = new ArrayList<>();
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
        //String input = " Mary had a little lamb . \n\n\n Peter  called for the wolf ,   and Aesop came .\n Cinderella likes shoes.. ";

        File text = new File("/Users/sundaramtiwari/Downloads/sample-files/large.in");
        Scanner sc = new Scanner(text);

        long startTime = System.currentTimeMillis();
//        Scanner sc = new Scanner(System.in);

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

        headers.forEach(System.out::println);

        String fileName = "/Users/sundaramtiwari/Downloads/book_new.csv";
        CSVFileWriter fileWriter = new CSVFileWriter(fileName, headers.toArray(String[]::new));
        fileWriter.writeToFile(sentences);

        //createCSVFile(sentences);
    }

    public static void createCSVFile(List<Sentence> input) throws IOException {
        FileWriter out = new FileWriter("/Users/sundaramtiwari/Downloads/book_new.csv");
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                .withHeader(headers.toArray(String[]::new)))) {
            input.forEach(sentence -> {
                sentence.getWord().forEach(word -> {
                    try {
                        printer.print(word);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                try {
                    printer.println();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}