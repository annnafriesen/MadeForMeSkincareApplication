package model;

//Represents a serum with a name, type, description, ingredients and price.
public class Serum implements Product {
    private final String productName;
    private final String productType;
    private final String productDescription;
    private final String productIngredients;
    private final double price;

    //REQUIRES:
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
}
