package model.products;

import model.products.Product;
import org.json.JSONObject;

//Represents a cleanser with a name, type, description, ingredient list and price.
public class Cleanser extends Product {

    //REQUIRES: cleanser description and ingredients must have non-zero 
    //EFFECTS: creates a cleanser with a unique name, description, ingredient list and price. Type is always
    // set to "cleanser"
    public Cleanser(String name, String description, String ingredients, double price) {
        super(name, description, "Cleanser", ingredients, price);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", productName);
        json.put("type", productType);
        json.put("description", productDescription);
        json.put("ingredients", productIngredients);
        json.put("price", price);
        return json;
    }
}
