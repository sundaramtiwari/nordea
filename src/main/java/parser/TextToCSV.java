package parser;

import entity.Sentence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import writer.CSVFileWriter;

public class TextToCSV {

  public void convertTextToCSV() throws IOException {
    Scanner sc = new Scanner(System.in);
    getCSVFileFromText(sc);
  }
  public void getCSVFileFromText(Scanner sc) throws IOException {
    List<Sentence> sentences = new ArrayList<>();
    List<String> words = new ArrayList<>();
    StringBuilder sbr = new StringBuilder();
    int numOfColumns = 0;

    while (sc.hasNextLine()) {
      String input = sc.nextLine();
      for (int i = 0; i < input.length(); i++) {
        char ch = input.charAt(i);
        if (Character.isLetterOrDigit(ch) || Utils.WORD_JOINERS.contains(ch)) {
          sbr.append(ch);

        } else if (Utils.SENTENCE_TERMINATORS.contains(ch)) {
          if ('.' == ch && Utils.ABBREVIATIONS.contains(sbr.toString())) {
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
    Utils.headers.add("");
    for (int i = 1; i <= numOfColumns; i++) {
      Utils.headers.add("Word " + i);
    }

    String fileName = "src/main/java/output.csv";
    CSVFileWriter fileWriter = new CSVFileWriter(fileName, Utils.headers.toArray(String[]::new));
    fileWriter.writeToFile(sentences);
  }
}
