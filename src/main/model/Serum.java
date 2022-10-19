package model;

import org.json.JSONObject;

//Represents a serum with a name, type, description, ingredients and price.
public class Serum implements Product {
    private final String productName;
    private final String productType;
    private final String productDescription;
    private final String productIngredients;
    private final double price;

    //EFFECTS: creates a serum with a unique name, description, ingredient list and price. Type is always set
    // to "serum"
    public Serum(String name, String description, String ingredients, double price) {
        this.productName = name;
        this.productType = "Serum";
        this.productDescription = description;
        this.productIngredients = ingredients;
        this.price = price;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public String getProductType() {
        return productType;
    }

    @Override
    public String getDescription() {
        return productDescription;
    }

    @Override
    public String getIngredients() {
        return productIngredients;
    }

    @Override
    public double getPrice() {
        return price;
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
