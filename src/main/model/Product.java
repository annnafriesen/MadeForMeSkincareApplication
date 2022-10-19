package model;

import org.json.JSONObject;
import persistence.Writeable;

//Represents a product with a name, type, description, ingredient list, and price.
public interface Product extends Writeable {
    String getProductName();

    String getProductType();

    String getDescription();

    String getIngredients();

    double getPrice();

}
