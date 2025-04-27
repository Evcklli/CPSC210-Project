package persistence;

import model.Word;
import model.WordList;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            WordList wr = new WordList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
        } catch (IOException e) {
            // test passes
        }
    }

    @Test
    void testWriterEmptyWordList() {
        try {
            WordList wordList = new WordList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWordList.json");
            writer.open();
            writer.write(wordList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWordList.json");
            wordList = reader.read();
            assertEquals(0, wordList.listWords().size(), "Expected word list to be empty");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWordList() {
        try {
            WordList wordList = new WordList();
            wordList.addWord(new Word("example1", "noun", "a sample word for testing"));
            wordList.addWord(new Word("example2", "verb", "another sample word for testing"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWordList.json");
            writer.open();
            writer.write(wordList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWordList.json");
            wordList = reader.read();
            assertEquals(2, wordList.listWords().size());

            checkWord("example1", "noun", "a sample word for testing", wordList.findWord("example1"));
            checkWord("example2", "verb", "another sample word for testing", wordList.findWord("example2"));

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
