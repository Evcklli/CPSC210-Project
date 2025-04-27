package model;

import persistence.Writable;

import org.json.JSONObject;

// Represents a vocabulary word with spelling, part of speech, and definition.

public class Word implements Writable {
    private String spelling;
    private String partOfSpeech;
    private String definition;

    // EFFECTS: creates a new Word with the given
    // spelling, part of speech, and definition
    public Word(String spelling, String partOfSpeech, String definition) {
        this.spelling = spelling;
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
    }

    // EFFECTS: returns the spelling of the word
    public String getSpelling() {
        return spelling;
    }

    // EFFECTS: returns the part of speech of the word
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    // EFFECTS: returns the definition of the word
    public String getDefinition() {
        return definition;
    }

    // REQUIRES: spelling is a non-empty string
    // EFFECTS: returns the first letter of the spelling
    public String getFirstLetter() {
        return spelling.substring(0, 1);
    }

    // MODIFIES: this
    // EFFECTS: sets the spelling of the word to the given value
    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    // MODIFIES: this
    // EFFECTS: sets the part of speech of the word to the given value
    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    // MODIFIES: this
    // EFFECTS: sets the definition of the word to the given value
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("spelling", spelling);
        json.put("partOfSpeech", partOfSpeech);
        json.put("definition", definition);
        return json;
    }
}
