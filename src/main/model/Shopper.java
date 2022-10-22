package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

// Represents a shopper with a name, skin type, and their max price range for shopping.
public class Shopper implements Writeable {
    private String customerName;
    private ConcernType concern;
    private SkinType skinType;
    private double maxPrice;

    //REQUIRES: Shopper name must be non-zero length and maxPrice must be > 0.
    //EFFECTS: creates a shopper object with an empty name, no declared skin or concern type, and maxPrice is 0.00.
    public Shopper() {
        this.customerName = "";
        this.concern = ConcernType.DEFAULT;
        this.skinType = SkinType.DEFAULT;
        this.maxPrice = 0.0;
    }

    //getters
    //EFFECTS: returns the shopper's list of concerns
    public String getCustomerName() {
        return customerName;
    }

    //EFFECTS: returns the shopper's list of concerns
    public ConcernType getConcern() {
        return concern;
    }

    //EFFECTS: returns the shopper's skin type
    public SkinType getSkinType() {
        return skinType;
    }

    //EFFECTS: returns the shopper's max price range
    public double getMaxPrice() {
        return maxPrice;
    }


    //REQUIRES: input n must be a non-empty string
    //MODIFIES: this
    //EFFECTS: sets customer name to n
    public void setName(String n) {
        this.customerName = n;
    }

    //MODIFIES: this
    //EFFECTS: adds concerns to the shoppers list of concerns
    public void setConcern(ConcernType c) {
        this.concern = c;
    }

    //MODIFIES: this
    //EFFECTS: sets the users skin type as either oily, combination or dry
    public void setSkinType(SkinType type) {
        this.skinType = type;
    }

    //REQUIRES: input i must be > 0.
    //MODIFIES: this
    //EFFECTS: sets the users max price as given
    public void setMaxPrice(double i) {
        this.maxPrice = i;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", customerName);
        json.put("skin type", skinType);
        json.put("max price", maxPrice);
        json.put("concern type", concern);
        return json;
    }
}

