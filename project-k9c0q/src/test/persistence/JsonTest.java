package persistence;

import model.Word;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkWord(String spelling, String partOfSpeech, String definition, Word word) {
        assertEquals(spelling, word.getSpelling());
        assertEquals(partOfSpeech, word.getPartOfSpeech());
        assertEquals(definition, word.getDefinition());
    }
}
