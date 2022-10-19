package persistence;

import org.json.JSONObject;

//REFERENCE LIST: the following code mimics behaviour seen in JsonSerializationDemo provided in CPSC 210, which can
// be found at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git.

public interface Writeable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
