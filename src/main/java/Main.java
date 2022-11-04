import java.io.IOException;
import parser.TextToCSV;

public class Main {
    public static void main(String[] args) throws IOException {
        TextToCSV textToCSV = new TextToCSV();
        textToCSV.convertTextToCSV();
    }
}