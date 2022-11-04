import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {

  public static final List<String> headers = new ArrayList<>();
  public static final Set<Character> SENTENCE_TERMINATORS = new HashSet<>();
  public static final Set<Character> WORD_JOINERS = new HashSet<>();
  public static final Set<String> ABBREVIATIONS = new HashSet<>();

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

}
