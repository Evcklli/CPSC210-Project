package persistence;

import model.Word;
import model.WordList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads WordList from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads WordList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WordList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWordList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses WordList from JSON object and returns it
    private WordList parseWordList(JSONObject jsonObject) {
        WordList wordList = new WordList();
        addWords(wordList, jsonObject);
        return wordList;
    }

    // MODIFIES: wordList
    // EFFECTS: parses words from JSON object and adds them to wordList
    private void addWords(WordList wordList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("words");
        for (Object json : jsonArray) {
            JSONObject nextWord = (JSONObject) json;
            addWord(wordList, nextWord);
        }
    }

    // MODIFIES: wordList
    // EFFECTS: parses word from JSON object and adds it to wordList
    private void addWord(WordList wordList, JSONObject jsonObject) {
        String spelling = jsonObject.getString("spelling");
        String partOfSpeech = jsonObject.getString("partOfSpeech");
        String definition = jsonObject.getString("definition");
        Word word = new Word(spelling, partOfSpeech, definition);
        wordList.addWord(word);
    }
}
