package model;

import java.util.ArrayList;
import java.util.List;

// Represents a shopper with a name, skin type, and their max price range for shopping.
public class Shopper {
    String customerName;
    List<ConcernType> concerns;
    SkinType skinType;
    double maxPrice;

    //REQUIRES: Shopper name must be non-zero length and maxPrice must be > 0.
    //EFFECTS: creates a shopper with a name, skin type, and maxPrice
    public Shopper(String name, SkinType skinType, Integer maxPrice) {
        this.customerName = name;
        this.concerns = new ArrayList<>();
        this.skinType = skinType;
        this.maxPrice = maxPrice;
    }

    //getters
    //EFFECTS: returns the shopper's list of concerns
    public String getCustomerName() {
        return customerName;
    }

    //EFFECTS: returns the shopper's list of concerns
    public List<ConcernType> getConcerns() {
        return concerns;
    }

    //EFFECTS: returns the shopper's skin type
    public SkinType getSkinType() {
        return skinType;
    }

    //EFFECTS: returns the shopper's max price range
    public double getMaxPrice() {
        return maxPrice;
    }


    //MODIFIES: this
    //EFFECTS: sets customer name to n
    public void setName(String n) {
        customerName = n;
    }


    //MODIFIES: this
    //EFFECTS: adds concerns to the shoppers list of concerns
    public void setConcerns(ConcernType c) {
        concerns.add(c);
    }

    //MODIFIES: this
    //EFFECTS: sets the users skin type as either oily, combination or dry
    public void setSkinType(SkinType type) {
        skinType = type;
    }

    //REQUIRES: input i must be > 0.
    //MODIFIES: this
    //EFFECTS: sets the users max price as given
    public void setMaxPrice(double i) {
        maxPrice = i;
    }

}

