package writer;

import entity.Sentence;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVFileWriter implements IWriter {

    private final String fileName;
    private final String[] headers;

    public CSVFileWriter(String fileName, String[] headers) {
        this.fileName = fileName;
        this.headers = headers;
    }

    @Override
    public void writeToFile(List<Sentence> content) throws IOException {
        FileWriter out = new FileWriter(fileName);
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                .withHeader(headers))) {
            content.forEach(sentence -> {
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
