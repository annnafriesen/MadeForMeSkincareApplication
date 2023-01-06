package persistence;

import model.types.ConcernType;
import model.products.Product;
import model.ShoppingCart;
import model.types.SkinType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//REFERENCE LIST: the following code mimics behaviour seen in JsonSerializationDemo which can
// be found at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git. My project uses this demo's
// strategy of testing a non-existent file, an empty file and a "normal" file.

//Tests methods in the JsonReader class
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ShoppingCart sc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyShoppingCart() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyShoppingCart.json");
        try {
            ShoppingCart sc = reader.read();
            assertEquals("", sc.getShopper().getCustomerName());
            assertEquals(0, sc.getShopper().getMaxPrice());
            assertEquals(SkinType.DEFAULT, sc.getShopper().getSkinType());
            assertEquals(ConcernType.DEFAULT, sc.getShopper().getConcern());
            assertEquals(0, sc.getProductsInCart().size());
            assertEquals(0, sc.getWishList().size());
            assertEquals(0, sc.getRecommendationList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralShoppingCart() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralShoppingCart.json");
        try {
            ShoppingCart sc = reader.read();
            List<Product> shoppingCart = sc.getProductsInCart();
            List<Product> wishList = sc.getWishList();
            List<Product> recommendationList = sc.getRecommendationList();
            assertEquals(4, shoppingCart.size());
            assertEquals(4, recommendationList.size());
            assertEquals(4, wishList.size());
            checkProduct("Azelaic Acid Suspension 10%", "A formula for uneven and blemish-prone skin.",
                    "Azelaic Acid", 27.2, "Serum", shoppingCart.get(0));
            checkProduct("Mandelic Acid 10%", "A gentler exfoliant, in serum form.",
                    "Mandelic Acid, Sodium Hyaluronate Crosspolymer, Tasmannia Lanceolata "
                            + "Fruit/Leaf Extract", 8.8, "Exfoliator", shoppingCart.get(1));
            checkProduct("Natural Moisturizer", "Keeps the outer layer "
                            + "of your skin protected and well-hydrated, without feeling greasy.",
                    "Sodium Hyaluronate, Arginine, Sodium PCA, PCA, Lactates, Lactic Acid, and Minerals",
                    11.50, "Moisturizer", shoppingCart.get(2));
            checkProduct("Squalance Cleanser", "A moisturizing all-in-one "
                            + "cleanser", "Squalane, Sucrose Stearate, Ethyl Macadamiate, Sucrose Laurate",
                    21.70, "Cleanser", shoppingCart.get(3));

            checkProduct("Azelaic Acid Suspension 10%", "A formula for uneven and blemish-prone skin.",
                    "Azelaic Acid", 27.2, "Serum", wishList.get(0));
            checkProduct("Mandelic Acid 10%", "A gentler exfoliant, in serum form.",
                    "Mandelic Acid, Sodium Hyaluronate Crosspolymer, Tasmannia Lanceolata "
                            + "Fruit/Leaf Extract", 8.8, "Exfoliator", wishList.get(1));
            checkProduct("Natural Moisturizer", "Keeps the outer layer "
                            + "of your skin protected and well-hydrated, without feeling greasy.",
                    "Sodium Hyaluronate, Arginine, Sodium PCA, PCA, Lactates, Lactic Acid, and Minerals",
                    11.50, "Moisturizer", wishList.get(2));
            checkProduct("Squalance Cleanser", "A moisturizing all-in-one "
                            + "cleanser", "Squalane, Sucrose Stearate, Ethyl Macadamiate, Sucrose Laurate",
                    21.70, "Cleanser", wishList.get(3));

            checkProduct("Azelaic Acid Suspension 10%", "A formula for uneven and blemish-prone skin.",
                    "Azelaic Acid", 27.2, "Serum", recommendationList.get(0));
            checkProduct("Mandelic Acid 10%", "A gentler exfoliant, in serum form.",
                    "Mandelic Acid, Sodium Hyaluronate Crosspolymer, Tasmannia Lanceolata "
                            + "Fruit/Leaf Extract", 8.8, "Exfoliator", recommendationList.get(1));
            checkProduct("Natural Moisturizer", "Keeps the outer layer "
                            + "of your skin protected and well-hydrated, without feeling greasy.",
                    "Sodium Hyaluronate, Arginine, Sodium PCA, PCA, Lactates, Lactic Acid, and Minerals",
                    11.50, "Moisturizer", recommendationList.get(2));
            checkProduct("Squalance Cleanser", "A moisturizing all-in-one "
                            + "cleanser", "Squalane, Sucrose Stearate, Ethyl Macadamiate, Sucrose Laurate",
                    21.70, "Cleanser", recommendationList.get(3));


            checkShopper(SkinType.COMBINATION, 100, ConcernType.REDNESS, "Anna", sc);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
