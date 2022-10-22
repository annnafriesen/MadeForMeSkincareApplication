package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.*;
import org.json.*;

//REFERENCE LIST: the following code mimics behaviour seen in JsonSerializationDemo provided in CPSC 210, which can
// be found at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git.

// Represents a reader that reads shopping cart from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads shopping cart from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ShoppingCart read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseShoppingCart(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses shopping cart from JSON object and returns it
    private ShoppingCart parseShoppingCart(JSONObject jsonObject) {
        Shopper s = new Shopper();
        ShoppingCart sc = new ShoppingCart(s);
        addShopper(sc, jsonObject);
        addProductsToShoppingCart(sc, jsonObject);
        addProductsToWishList(sc, jsonObject);
        addProductsToRecommendationList(sc, jsonObject);
        return sc;
    }

    // MODIFIES: s
    // EFFECTS: parses shoppers questionnaire answers from JSON object and adds them to shopper
    private void addShopper(ShoppingCart sc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("shopper");
        for (Object json : jsonArray) {
            JSONObject nextProduct = (JSONObject) json;
            parseShopper(sc, nextProduct);
        }
    }

    // MODIFIES: s
    // EFFECTS: parses shoppers questionnaire answers from JSON object and adds them to shopper
    private void parseShopper(ShoppingCart sc, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        SkinType skinType = SkinType.valueOf(jsonObject.getString("skin type"));
        int maxPrice = jsonObject.getInt("max price");
        ConcernType concern = ConcernType.valueOf(jsonObject.getString("concern type"));
        sc.getShopper().setSkinType(skinType);
        sc.getShopper().setConcern(concern);
        sc.getShopper().setName(name);
        sc.getShopper().setMaxPrice(maxPrice);
    }

    // MODIFIES: sc
    // EFFECTS: parses products from JSON object and adds them to shopping cart
    private void addProductsToShoppingCart(ShoppingCart sc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("shopping cart");
        for (Object json : jsonArray) {
            JSONObject nextProduct = (JSONObject) json;
            addProductToShoppingCart(sc, nextProduct);
        }
    }

    // MODIFIES: sc
    // EFFECTS: parses product from JSON object and adds it to list in shopping cart
    private void addProductToShoppingCart(ShoppingCart sc, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        String description = jsonObject.getString("description");
        String ingredients = jsonObject.getString("ingredients");
        int price = jsonObject.getInt("price");

        Product product;
        switch (type) {
            case "Moisturizer":
                product = new Moisturizer(name, description, ingredients, price);
                break;
            case "Serum":
                product = new Serum(name, description, ingredients, price);
                break;
            case "Exfoliator":
                product = new Exfoliator(name, description, ingredients, price);
                break;
            default:
                product = new Cleanser(name, description, ingredients, price);
                break;
        }

        sc.addProductToCart(product);
    }

    // MODIFIES: sc
    // EFFECTS: parses products from JSON object and adds them to wish list
    private void addProductsToWishList(ShoppingCart sc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("wish list");
        for (Object json : jsonArray) {
            JSONObject nextProduct = (JSONObject) json;
            addProductToWishlist(sc, nextProduct);
        }
    }

    // MODIFIES: sc
    // EFFECTS: parses product from JSON object and adds it to list in wish list
    private void addProductToWishlist(ShoppingCart sc, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        String description = jsonObject.getString("description");
        String ingredients = jsonObject.getString("ingredients");
        int price = jsonObject.getInt("price");

        Product product;
        switch (type) {
            case "Moisturizer":
                product = new Moisturizer(name, description, ingredients, price);
                break;
            case "Serum":
                product = new Serum(name, description, ingredients, price);
                break;
            case "Exfoliator":
                product = new Exfoliator(name, description, ingredients, price);
                break;
            default:
                product = new Cleanser(name, description, ingredients, price);
                break;
        }

        sc.getWishList().add(product);
    }

    // MODIFIES: sc
    // EFFECTS: parses products from JSON object and adds them to list in recommendation list
    private void addProductsToRecommendationList(ShoppingCart sc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("recommendation list");
        for (Object json : jsonArray) {
            JSONObject nextProduct = (JSONObject) json;
            addProductToRecommendationList(sc, nextProduct);
        }
    }

    // MODIFIES: sc
    // EFFECTS: parses product from JSON object and adds it to list in recommendation list
    private void addProductToRecommendationList(ShoppingCart sc, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        String description = jsonObject.getString("description");
        String ingredients = jsonObject.getString("ingredients");
        int price = jsonObject.getInt("price");

        Product product;
        switch (type) {
            case "Moisturizer":
                product = new Moisturizer(name, description, ingredients, price);
                break;
            case "Serum":
                product = new Serum(name, description, ingredients, price);
                break;
            case "Exfoliator":
                product = new Exfoliator(name, description, ingredients, price);
                break;
            default:
                product = new Cleanser(name, description, ingredients, price);
                break;
        }

        sc.addProductToRecommendationList(product);
    }

}

