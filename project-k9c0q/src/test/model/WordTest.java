package model;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordTest {

    private Word word;

    @BeforeEach
    public void setUp() {
        word = new Word("apple", "noun", "a fruit");
    }

    @Test
    public void testConstructor() {
        assertEquals("apple", word.getSpelling());
        assertEquals("noun", word.getPartOfSpeech());
        assertEquals("a fruit", word.getDefinition());
    }

    @Test
    public void testSetSpelling() {
        word.setSpelling("banana");
        assertEquals("banana", word.getSpelling());
    }

    @Test
    public void testSetPartOfSpeech() {
        word.setPartOfSpeech("verb");
        assertEquals("verb", word.getPartOfSpeech());
    }

    @Test
    public void testSetDefinition() {
        word.setDefinition("a yellow fruit");
        assertEquals("a yellow fruit", word.getDefinition());
    }

    @Test
    public void testGetFirstLetter() {
        assertEquals("a", word.getFirstLetter());
    }

}
