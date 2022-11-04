package writer;

import entity.Sentence;

import java.io.IOException;
import java.util.List;

public interface IWriter {

    void writeToFile(List<Sentence> content) throws IOException;
}
