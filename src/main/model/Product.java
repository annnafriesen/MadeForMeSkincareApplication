package model;

//Represents a product with a name, type, description, ingredient list, and price.
public interface Product {
    String getProductName();

    String getProductType();

    String getDescription();

    String getIngredients();

    double getPrice();

}
