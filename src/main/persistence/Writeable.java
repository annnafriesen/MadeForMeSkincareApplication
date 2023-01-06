package persistence;

import org.json.JSONObject;

//REFERENCE LIST: the following code mimics behaviour seen in JsonSerializationDemo which can
// be found at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git. I use this demo's strategy of
// creating an interface for transforming shopping cart's field into JsonObjects.

public interface Writeable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
