package model;

import model.events.Event;
import model.events.EventLog;
import model.products.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.List;

// Represents a virtual shopping cart for the specified shopper, and includes the total cost of cart, products
// in the cart, and a wishlist.
public class ShoppingCart implements Writeable {
    private List<Product> productsInCart;
    private List<Product> wishList;
    private List<Product> recommendationList;
    private double totalCost;
    private Shopper shopper;

    public static final double DISCOUNT = 0.15;
    public static final double AMOUNT_NEEDED_FOR_DISCOUNT = 50;

    //REQUIRES: price must be >= 0 and price = 0 only when cart is empty
    //MODIFIES: this
    //EFFECTS: creates a shopping cart with an empty cart and initial cart total of $0.00.
    public ShoppingCart(Shopper shopper) {
        this.shopper = shopper;
        this.productsInCart = new ArrayList<>();
        this.totalCost = 0.0;
        this.wishList = new ArrayList<>();
        this.recommendationList = new ArrayList<>();
    }

    //EFFECTS: returns a list of all the products in the shopping cart
    public Shopper getShopper() {
        return shopper;
    }

    //EFFECTS: returns a list of all the products in the shopping cart
    public List<Product> getProductsInCart() {
        return productsInCart;
    }

    //EFFECTS: returns a list of all the products in the recommendation list
    public List<Product> getRecommendationList() {
        return recommendationList;
    }

    //EFFECTS: returns a list of all the products in the wish list
    public List<Product> getWishList() {
        return wishList;
    }

    //EFFECTS: returns the total price of all products in shopping cart
    public double getTotalPrice() {
        return totalCost;
    }


    //MODIFIES: this
    //EFFECTS: adds a product to the shopping cart if the total cost of the cart (including the new product)
    //         is less than the shoppers max price limit and returns true. Otherwise, returns false and adds
    //         product to a wishlist for future purchases.
    public Boolean addProductToCart(Product p) {
        if (p.getPrice() + this.totalCost <= shopper.getMaxPrice()) {
            this.productsInCart.add(p);
            this.totalCost += p.getPrice();
            EventLog.getInstance().logEvent(new Event("Added product: " + p.getProductName()));
            return true;
        } else {
            wishList.add(p);
            return false;
        }
    }

    //MODIFIES: this
    //EFFECTS: removes a product from the shopping cart
    public void removeProductFromCart(Product p) {
        this.productsInCart.remove(p);
        this.totalCost -= p.getPrice();
        EventLog.getInstance().logEvent(new Event("Removed product: " + p.getProductName()));
    }

    //MODIFIES: this
    //EFFECTS: adds products to recommendation list
    public void addProductToRecommendationList(Product p) {
        this.recommendationList.add(p);
    }

    //MODIFIES: this
    //EFFECTS: if total cost is above given amount, apply discount and return true.
    //         Otherwise, return false.
    //         Only apply when user says they are ready to pay.
    public Boolean checkForDiscount() {
        if (totalCost > AMOUNT_NEEDED_FOR_DISCOUNT) {
            totalCost = totalCost * (1.00 - DISCOUNT);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("shopping cart", cartToJson());
        json.put("recommendation list", recommendationListToJson());
        json.put("wish list", wishListToJson());
        json.put("shopper", shopperToJson());
        return json;
    }

    // EFFECTS: returns shopping cart list in this shopping cart as a JSON array
    private JSONArray cartToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Product p : getProductsInCart()) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns recommendation list in this shopping cart as a JSON array
    private JSONArray recommendationListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Product p : getRecommendationList()) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns wish list in this shopping cart as a JSON array
    private JSONArray wishListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Product p : getWishList()) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns shopper details in this shopping cart as a JSON array
    private JSONArray shopperToJson() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(getShopper().toJson());
        return jsonArray;
    }

    //EFFECTS: prints log of events in eventLog
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }

}

