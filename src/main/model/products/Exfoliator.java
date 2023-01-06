package model.products;

import model.products.Product;
import org.json.JSONObject;

//Represents an exfoliator with a name, type, description, ingredient list and price.
public class Exfoliator extends Product {

    //EFFECTS: creates an exfoliator with a unique name, description, ingredient list and price. Type is always set
    // to "exfoliator"
    public Exfoliator(String name, String description, String ingredients, double price) {
        super(name, description, "Exfoliator", ingredients, price);
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


