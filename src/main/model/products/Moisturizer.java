package model.products;

import model.products.Product;
import org.json.JSONObject;

//Represents a moisturizer with a name, type, description, ingredients and price.
public class Moisturizer extends Product {

    //EFFECTS: creates a cleanser with a unique name, description, ingredient list and price. Type is always set to
    // "moisturizer".
    public Moisturizer(String name, String description, String ingredients, double price) {
        super(name, description, "Moisturizer", ingredients, price);
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
