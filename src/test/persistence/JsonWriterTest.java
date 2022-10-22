package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            Shopper s = new Shopper();
            ShoppingCart sc = new ShoppingCart(s);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyShoppingCart() {
        try {
            Shopper s = new Shopper();
            ShoppingCart sc = new ShoppingCart(s);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyShoppingCart.json");
            writer.open();
            writer.writeShoppingCart(sc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyShoppingCart.json");
            sc = reader.read();
            assertEquals("", sc.getShopper().getCustomerName());
            assertEquals(0, sc.getShopper().getMaxPrice());
            assertEquals(SkinType.DEFAULT, sc.getShopper().getSkinType());
            assertEquals(ConcernType.DEFAULT, sc.getShopper().getConcern());
            assertEquals(0, sc.getProductsInCart().size());
            assertEquals(0, sc.getWishList().size());
            assertEquals(0, sc.getRecommendationList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralShoppingCart() {
        try {
            Shopper s = new Shopper();
            ShoppingCart sc = new ShoppingCart(s);

            sc.addProductToRecommendationList(new Serum("Azelaic Acid Suspension 10%", "A "
                    + "formula for uneven and blemish-prone skin.", "Azelaic Acid", 27.20));
            sc.addProductToRecommendationList(new Exfoliator("Mandelic Acid 10%", "A gentler exfoliant, "
                    + "in serum form.", "Mandelic Acid, Sodium Hyaluronate Crosspolymer, Tasmannia "
                    + "Lanceolata Fruit/Leaf Extract", 8.80));
            sc.addProductToRecommendationList(new Moisturizer("Natural Moisturizer", "Keeps the outer layer "
                    + "of your skin protected and well-hydrated, without feeling greasy.", "Sodium Hyaluronate, "
                    + "Arginine, Sodium PCA, PCA, Lactates, Lactic Acid, and Minerals", 11.50));
            sc.addProductToRecommendationList(new Cleanser("Squalance Cleanser", "A moisturizing all-in-one "
                    + "cleanser", "Squalane, Sucrose Stearate, Ethyl Macadamiate, Sucrose Laurate",
                    21.70));

            sc.getWishList().add((new Serum("Azelaic Acid Suspension 10%", "A "
                    + "formula for uneven and blemish-prone skin.", "Azelaic Acid", 27.20)));
            sc.getWishList().add((new Exfoliator("Mandelic Acid 10%", "A gentler exfoliant, "
                    + "in serum form.", "Mandelic Acid, Sodium Hyaluronate Crosspolymer, Tasmannia "
                    + "Lanceolata Fruit/Leaf Extract", 8.80)));
            sc.getWishList().add((new Moisturizer("Natural Moisturizer", "Keeps the outer layer "
                    + "of your skin protected and well-hydrated, without feeling greasy.", "Sodium Hyaluronate, "
                    + "Arginine, Sodium PCA, PCA, Lactates, Lactic Acid, and Minerals", 11.50)));
            sc.getWishList().add((new Cleanser("Squalance Cleanser", "A moisturizing all-in-one "
                    + "cleanser", "Squalane, Sucrose Stearate, Ethyl Macadamiate, Sucrose Laurate",
                    21.70)));

            sc.getShopper().setName("Anna");
            sc.getShopper().setConcern(ConcernType.REDNESS);
            sc.getShopper().setSkinType(SkinType.COMBINATION);
            sc.getShopper().setMaxPrice(100);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralShoppingCart.json");
            writer.open();
            writer.writeShoppingCart(sc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralShoppingCart.json");
            sc = reader.read();
            List<Product> shoppingCart = sc.getProductsInCart();
            List<Product> wishList = sc.getWishList();
            List<Product> recommendationList = sc.getRecommendationList();
            assertEquals(0, shoppingCart.size());
            assertEquals(4, recommendationList.size());
            assertEquals(4, wishList.size());

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
            fail("Exception should not have been thrown");
        }
    }
}
