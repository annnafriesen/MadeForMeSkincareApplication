package model;

import model.events.Event;
import model.events.EventLog;
import model.products.Exfoliator;
import model.products.Moisturizer;
import model.products.Product;
import model.products.Serum;
import model.types.ConcernType;
import model.types.SkinType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.ShoppingCart.AMOUNT_NEEDED_FOR_DISCOUNT;
import static model.ShoppingCart.DISCOUNT;
import static org.junit.jupiter.api.Assertions.*;

//Tests methods in the ShoppingCart class
public class ShoppingCartTest {
    private ShoppingCart shoppingCart;
    private Shopper shopper;

    @BeforeEach
    public void setup() {
        shopper = new Shopper();
        shopper.setName("Julia");
        shopper.setSkinType(SkinType.COMBINATION);
        shopper.setConcern(ConcernType.ACNE);
        shopper.setMaxPrice(55);
        shoppingCart = new ShoppingCart(shopper);
    }

    @Test
    void testPrintLog() {
        shoppingCart.addProductToCart(new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 13.50));
        shoppingCart.removeProductFromCart(new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 13.50));
        shoppingCart.printLog(EventLog.getInstance());
        List<Event> events = new ArrayList<>(EventLog.getInstance().getEvents());
        assertEquals("Event log cleared.", events.get(0).getDescription());
        assertEquals("Set shopper's name: Julia", events.get(1).getDescription());
        assertEquals("Set shopper's skin type: COMBINATION", events.get(2).getDescription());
        assertEquals("Set shopper's concern: ACNE", events.get(3).getDescription());
        assertEquals("Set shopper's price: $55.00", events.get(4).getDescription());
        assertEquals("Added product: Natural Moisturizer", events.get(5).getDescription());
    }

    @Test
    void testAddProductOneProduct() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 13.50);
        assertEquals(shopper, shoppingCart.getShopper());
        assertTrue(shoppingCart.addProductToCart(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(13.50, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());
        assertTrue(EventLog.getInstance().getEvents().contains(new
                Event("Added product: Natural Moisturizer")));
    }

    @Test
    void testAddProductTwoProductsNoDiscount() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 13.50);
        Product serum1 = new Serum("Natural Serum",
                "Made for soft skin", "Water", 10.00);
        assertTrue(shoppingCart.addProductToCart(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(13.50, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());

        assertTrue(shoppingCart.addProductToCart(serum1));
        assertEquals(2, shoppingCart.getProductsInCart().size());
        assertEquals(23.50, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());
    }

    @Test
    void testAddProductTwoProductsWithDiscount() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 25.00);
        Product serum1 = new Serum("Natural Serum",
                "Made for soft skin", "Water", 26.00);
        assertTrue(shoppingCart.addProductToCart(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(25.00, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());

        assertTrue(shoppingCart.addProductToCart(serum1));
        assertEquals(2, shoppingCart.getProductsInCart().size());
        assertTrue(shoppingCart.checkForDiscount());
        assertEquals(43.35, shoppingCart.getTotalPrice());
    }

    @Test
    void testCartTotalEqualsMaxPrice() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 25.00);
        Product serum1 = new Serum("Natural Serum",
                "Made for soft skin", "Water", 20);
        shopper.setMaxPrice(45);
        assertTrue(shoppingCart.addProductToCart(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(25.00, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());
        assertEquals(0, shoppingCart.getWishList().size());

        assertTrue(shoppingCart.addProductToCart(serum1));
        assertEquals(2, shoppingCart.getProductsInCart().size());
        assertEquals(45, shoppingCart.getTotalPrice());
        assertEquals(0, shoppingCart.getWishList().size());
    }

    @Test
    void testAddToWishListMaxPriceReached() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 25.00);
        Product serum1 = new Serum("Natural Serum",
                "Made for soft skin", "Water", 30.01);
        assertTrue(shoppingCart.addProductToCart(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(25.00, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());
        assertEquals(0, shoppingCart.getWishList().size());

        assertFalse(shoppingCart.addProductToCart(serum1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(25.00, shoppingCart.getTotalPrice());
        assertEquals(1, shoppingCart.getWishList().size());
        assertTrue(shoppingCart.getWishList().contains(serum1));
    }


    @Test
    void testAddTwoToWishList() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 25.00);
        Product serum1 = new Serum("Natural Serum",
                "Made for soft skin", "Water", 30.01);
        Product exfoliant = new Exfoliator("Natural Exfoliator",
                "Removes debris", "Glycolic Acid", 30.00);
        assertTrue(shoppingCart.addProductToCart(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(25.00, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());
        assertEquals(0, shoppingCart.getWishList().size());

        assertFalse(shoppingCart.addProductToCart(serum1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(25.00, shoppingCart.getTotalPrice());
        assertEquals(1, shoppingCart.getWishList().size());

        assertFalse(shoppingCart.addProductToCart(serum1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(25.00, shoppingCart.getTotalPrice());
        assertEquals(2, shoppingCart.getWishList().size());

    }

    @Test
    void testAddToRecommendationList() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 25.00);
        Product serum1 = new Serum("Natural Serum",
                "Made for soft skin", "Water", 30.01);
        shoppingCart.addProductToRecommendationList(moisturizer1);
        assertEquals(1, shoppingCart.getRecommendationList().size());
        assertTrue(shoppingCart.getRecommendationList().contains(moisturizer1));

        shoppingCart.addProductToRecommendationList(serum1);
        assertEquals(2, shoppingCart.getRecommendationList().size());
        assertTrue(shoppingCart.getRecommendationList().contains(moisturizer1));
        assertTrue(shoppingCart.getRecommendationList().contains(serum1));
    }

    @Test
    void testRemoveProductOneProduct() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 13.50);
        shoppingCart.addProductToCart(moisturizer1);
        shoppingCart.removeProductFromCart(moisturizer1);
        assertTrue(EventLog.getInstance().getEvents().contains(new
                Event("Removed product: Natural Moisturizer")));
        assertEquals(0, shoppingCart.getProductsInCart().size());
        assertEquals(0.0, shoppingCart.getTotalPrice());
    }

    @Test
    void testRemoveProductTwoProducts() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 13.50);
        Product serum1 = new Serum("Natural Serum",
                "Made for soft skin", "Water", 10.00);
        assertTrue(shoppingCart.addProductToCart(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(13.50, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());

        assertTrue(shoppingCart.addProductToCart(serum1));
        assertEquals(2, shoppingCart.getProductsInCart().size());
        assertEquals(23.50, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());

        shoppingCart.removeProductFromCart(serum1);
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(13.50, shoppingCart.getTotalPrice());

        shoppingCart.removeProductFromCart(moisturizer1);
        assertEquals(0, shoppingCart.getProductsInCart().size());
        assertEquals(0.0, shoppingCart.getTotalPrice());
    }

    @Test
    void testCheckForDiscountNoDiscount() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 13.50);
        assertTrue(shoppingCart.addProductToCart(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(13.50, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());
    }

    @Test
    void testCheckForDiscountOneCentBelowDiscount() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera",
                AMOUNT_NEEDED_FOR_DISCOUNT);
        assertTrue(shoppingCart.addProductToCart(moisturizer1));
        assertFalse(shoppingCart.checkForDiscount());
    }

    @Test
    void testCheckForDiscountExactAmountRequired() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera",
                (AMOUNT_NEEDED_FOR_DISCOUNT / (1 - DISCOUNT)) + 0.01);
        shopper.setMaxPrice((AMOUNT_NEEDED_FOR_DISCOUNT / (1 - DISCOUNT)) + 1);
        assertTrue(shoppingCart.addProductToCart(moisturizer1));
        assertTrue(shoppingCart.checkForDiscount());
    }

    @Test
    void testCheckForDiscountOneAboveDiscount() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera",
                (AMOUNT_NEEDED_FOR_DISCOUNT / (1 - DISCOUNT)) + 0.02);
        shopper.setMaxPrice((AMOUNT_NEEDED_FOR_DISCOUNT / (1 - DISCOUNT)) + 1);
        assertTrue(shoppingCart.addProductToCart(moisturizer1));
        assertTrue(shoppingCart.checkForDiscount());
    }

}
