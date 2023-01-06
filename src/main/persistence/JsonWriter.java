package persistence;

import model.Shopper;
import model.ShoppingCart;
import org.json.JSONObject;

import java.io.*;

//REFERENCE LIST: the following code mimics behaviour seen in JsonSerializationDemo which can
// be found at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git. I use the same five methods to
// write my arrays as seen in JsonSerializationDemo.

// Represents a writer that writes JSON representation of shopping cart to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }


    // MODIFIES: this
    // EFFECTS: writes JSON representation of shopping cart to file
    public void writeShoppingCart(ShoppingCart sc) {
        JSONObject json = sc.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

