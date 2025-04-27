package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;



public class WordListTest {
    @Test
    public void testAddWord() {
        WordList wordList = new WordList();
        Word word = new Word("apple", "noun", "a fruit");
        wordList.addWord(word);
        assertEquals(1, wordList.listWords().size());
    }

    @Test
    public void testAddWordMultipleTimes() {
        WordList wordList = new WordList();
        Word word = new Word("apple", "noun", "a fruit");
        wordList.addWord(word);
        assertEquals(1, wordList.listWords().size());
        Word word1 = new Word("banana", "noun", "a fruit");
        wordList.addWord(word1);
        assertEquals(2, wordList.listWords().size());
    }

    @Test
    public void testFindWord() {
        WordList wordList = new WordList();
        Word word = new Word("apple", "noun", "a fruit");
        wordList.addWord(word);
        assertEquals(1, wordList.listWords().size());
        Word word1 = new Word("banana", "noun", "a fruit");
        wordList.addWord(word1);
        assertEquals(2, wordList.listWords().size());
        assertEquals("apple", wordList.findWord("apple").getSpelling());
        assertEquals("banana", wordList.findWord("banana").getSpelling());
        assertEquals("", wordList.findWord("bahjs").getSpelling());
    }

    @Test
    public void testfilterByFirstLetter() {
        WordList wordList = new WordList();
        wordList = new WordList();
        wordList.addWord(new Word("apple", "a", "a"));
        wordList.addWord(new Word("banana", "a", "a"));
        wordList.addWord(new Word("appli", "a", "a"));
        wordList.addWord(new Word("berry", "a", "a"));
        ArrayList<String> filteredWords = wordList.filterByFirstLetter("a");
        assertTrue(filteredWords.contains("apple"));
        assertTrue(filteredWords.contains("appli"));
        assertFalse(filteredWords.contains("banana"));
        assertFalse(filteredWords.contains("berry"));
        assertEquals(2, filteredWords.size());
    }
}