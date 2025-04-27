package persistence;

import model.Word;
import model.WordList;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {

        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WordList wr = reader.read();
            fail("IOException expected");
        } catch (IOException | JSONException e) {

            // pass
        }
    }

    @Test
    void testReaderEmptyWordList() {
        JsonReader reader = new JsonReader("./data/testEmptyWordList.json");
        try {
            WordList wordList = reader.read();
            assertEquals(0, wordList.listWords().size(), "Expected word list to be empty");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testReaderGeneralWordList() {
        JsonReader reader = new JsonReader("./data/testWordList.json");
        try {
            WordList wordList = reader.read();
            assertEquals(2, wordList.listWords().size(), "Expected word list to contain 2 words");

            Word firstWord = wordList.findWord("1");
            assertEquals("1", firstWord.getSpelling());
            assertEquals("noun", firstWord.getPartOfSpeech());
            assertEquals("1", firstWord.getDefinition());

            Word secondWord = wordList.findWord("2");
            assertEquals("2", secondWord.getSpelling());
            assertEquals("verb", secondWord.getPartOfSpeech());
            assertEquals("2", secondWord.getDefinition());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
