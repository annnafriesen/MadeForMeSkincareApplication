package persistence;

import model.ConcernType;
import model.Product;
import model.ShoppingCart;
import model.SkinType;

import static org.junit.jupiter.api.Assertions.assertEquals;

//REFERENCE LIST: the following code mimics behaviour seen in JsonSerializationDemo provided in CPSC 210, which can
// be found at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git. Similar to demo's check function,
// except specific to my project.

//Tests the transcription of products and shopper details into JsonObjects
public class JsonTest {
    //EFFECTS: tests each product field for a product
    protected void checkProduct(String name, String description, String ingredients, double maxPrice,
                                String type, Product p) {
            assertEquals(name, p.getProductName());
            assertEquals(description, p.getDescription());
            assertEquals(ingredients, p.getIngredients());
            assertEquals(maxPrice, p.getPrice(), 0.9);
            assertEquals(type, p.getProductType());
    }

    //EFFECTS: tests each field for the shopping cart's shopper
    protected void checkShopper(SkinType skinType, int maxPrice, ConcernType concern, String name,
                                ShoppingCart sc) {
        assertEquals(skinType, sc.getShopper().getSkinType());
        assertEquals(maxPrice, sc.getShopper().getMaxPrice(), 0.9);
        assertEquals(concern, sc.getShopper().getConcern());
        assertEquals(name, sc.getShopper().getCustomerName());
    }
}
