package persistence;

import org.json.JSONObject;

// Represents an entity that can be converted to JSON
public interface Writable {
    JSONObject toJson();
}
