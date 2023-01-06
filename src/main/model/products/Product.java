package model.products;

import persistence.Writeable;

//Represents a product with a name, type, description, ingredient list, and price.
public abstract class Product implements Writeable {
    protected final String productName;
    protected final String productType;
    protected final String productDescription;
    protected final String productIngredients;
    protected final double price;

    public Product(String name, String description, String type, String ingredients, double price) {
        this.productName = name;
        this.productType = type;
        this.productDescription = description;
        this.productIngredients = ingredients;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductType() {
        return productType;
    }

    public String getDescription() {
        return productDescription;
    }

    public String getIngredients() {
        return productIngredients;
    }

    public double getPrice() {
        return price;
    }

}
