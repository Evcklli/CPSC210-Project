package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

//Represents a list of vocabulary words.

public class WordList implements Writable {
    private ArrayList<Word> words;

    // EFFECTS: initializes an empty list of words
    public WordList() {
        this.words = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given word to the list
    public void addWord(Word word) {
        this.words.add(word);
        EventLog.getInstance().logEvent(new Event("Word \"" + word.getSpelling() + "\" added to word list."));

    }

    // EFFECTS: returns a list of all word spellings in the list
    public ArrayList<String> listWords() {
        ArrayList<String> listWords = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            Word wordsith = words.get(i);
            String wordspelling = wordsith.getSpelling();
            listWords.add(wordspelling);

        }
        return listWords;

    }

    // EFFECTS: returns the word with the given spelling,
    // or an empty word object if not found
    public Word findWord(String spelling) {
        ArrayList<Word> wordssearch = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            Word wordsith = words.get(i);
            String spellingwordsith = wordsith.getSpelling();
            if (spellingwordsith.equals(spelling)) {
                wordssearch.add(wordsith);
            }
        }
        if (wordssearch.size() == 0) {
            Word newword = new Word("", "", "");
            return newword;
        } else {
            EventLog.getInstance().logEvent(new Event("Find Words " + spelling + "."));
            return wordssearch.get(0);
        }

    }

    // EFFECTS: Filter words by first letter
    public ArrayList<String> filterByFirstLetter(String firstLetter) {
        ArrayList<String> filteredWords = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            Word wordsith = words.get(i);
            if (wordsith.getFirstLetter().equals(firstLetter)) {
                filteredWords.add(wordsith.getSpelling());
                EventLog.getInstance().logEvent(new Event("Filter Words by " + firstLetter + "."));
            }
        }
        return filteredWords;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("words", wordsToJson());
        return json;
    }

    // EFFECTS: returns words in this word list as a JSON array
    private JSONArray wordsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Word w : words) {
            jsonArray.put(w.toJson());
        }
        return jsonArray;
    }


}
