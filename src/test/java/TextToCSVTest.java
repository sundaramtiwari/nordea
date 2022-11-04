import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import parser.TextToCSV;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextToCSVTest {

  private static final String GENERATED_CSV_FILE_PATH = "src/main/java/output.csv";

  @Test
  public void textToCSVParserTest() throws IOException {
    try {
      TextToCSV textToCSV = new TextToCSV();
      File text = new File("src/test/resources/small.in");
      Scanner sc = new Scanner(text);
      textToCSV.getCSVFileFromText(sc);
      File csvGeneratedFilePath = new File(GENERATED_CSV_FILE_PATH);
      assertTrue(csvGeneratedFilePath.exists());
    } finally {
      Files.deleteIfExists(Paths.get(GENERATED_CSV_FILE_PATH));
    }
  }


}
