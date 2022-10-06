package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.ShoppingCart.AMOUNT_NEEDED_FOR_DISCOUNT;
import static model.ShoppingCart.DISCOUNT;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {
    private ShoppingCart shoppingCart;
    private Shopper shopper;

    @BeforeEach
    public void setup() {
        shopper = new Shopper("Julia", SkinType.COMBINATION, 55);
        shoppingCart = new ShoppingCart(shopper);
    }

    @Test
    void testAddProductOneProduct() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 13.50);
        assertTrue(shoppingCart.addProduct(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(13.50, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());
    }

    @Test
    void testAddProductTwoProductsNoDiscount() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 13.50);
        Product serum1 = new Serum("Natural Serum",
                "Made for soft skin", "Water", 10.00);
        assertTrue(shoppingCart.addProduct(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(13.50, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());

        assertTrue(shoppingCart.addProduct(serum1));
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
        assertTrue(shoppingCart.addProduct(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(25.00, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());

        assertTrue(shoppingCart.addProduct(serum1));
        assertEquals(2, shoppingCart.getProductsInCart().size());
        assertEquals(43.35, shoppingCart.getTotalPrice());
    }

    @Test
    void testCartTotalEqualsMaxPrice() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 25.00);
        Product serum1 = new Serum("Natural Serum",
                "Made for soft skin", "Water", 20);
        shopper.setMaxPrice(45);
        assertTrue(shoppingCart.addProduct(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(25.00, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());
        assertEquals(0, shoppingCart.getWishList().size());

        assertTrue(shoppingCart.addProduct(serum1));
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
        assertTrue(shoppingCart.addProduct(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(25.00, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());
        assertEquals(0, shoppingCart.getWishList().size());

        assertFalse(shoppingCart.addProduct(serum1));
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
        assertTrue(shoppingCart.addProduct(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(25.00, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());
        assertEquals(0, shoppingCart.getWishList().size());

        assertFalse(shoppingCart.addProduct(serum1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(25.00, shoppingCart.getTotalPrice());
        assertEquals(1, shoppingCart.getWishList().size());

        assertFalse(shoppingCart.addProduct(serum1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(25.00, shoppingCart.getTotalPrice());
        assertEquals(2, shoppingCart.getWishList().size());

    }

    @Test
    void testRemoveProductOneProduct() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 13.50);
        shoppingCart.addProduct(moisturizer1);
        shoppingCart.removeProduct(moisturizer1);
        assertEquals(0, shoppingCart.getProductsInCart().size());
        assertEquals(0.0, shoppingCart.getTotalPrice());
    }

    @Test
    void testRemoveProductTwoProducts() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 13.50);
        Product serum1 = new Serum("Natural Serum",
                "Made for soft skin", "Water", 10.00);
        assertTrue(shoppingCart.addProduct(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(13.50, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());

        assertTrue(shoppingCart.addProduct(serum1));
        assertEquals(2, shoppingCart.getProductsInCart().size());
        assertEquals(23.50, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());

        shoppingCart.removeProduct(serum1);
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(13.50, shoppingCart.getTotalPrice());

        shoppingCart.removeProduct(moisturizer1);
        assertEquals(0, shoppingCart.getProductsInCart().size());
        assertEquals(0.0, shoppingCart.getTotalPrice());
    }

    @Test
    void testCheckForDiscountNoDiscount() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera", 13.50);
        assertTrue(shoppingCart.addProduct(moisturizer1));
        assertEquals(1, shoppingCart.getProductsInCart().size());
        assertEquals(13.50, shoppingCart.getTotalPrice());
        assertFalse(shoppingCart.checkForDiscount());
    }

    @Test
    void testCheckForDiscountOneCentBelowDiscount() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera",
                AMOUNT_NEEDED_FOR_DISCOUNT);
        assertTrue(shoppingCart.addProduct(moisturizer1));
        assertFalse(shoppingCart.checkForDiscount());
    }

    @Test
    void testCheckForDiscountExactAmountRequired() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera",
                (AMOUNT_NEEDED_FOR_DISCOUNT/(1 - DISCOUNT)) + 0.01);
        shopper.setMaxPrice((AMOUNT_NEEDED_FOR_DISCOUNT/(1 - DISCOUNT)) + 1);
        assertTrue(shoppingCart.addProduct(moisturizer1));
        assertTrue(shoppingCart.checkForDiscount());
    }

    @Test
    void testCheckForDiscountOneAboveDiscount() {
        Product moisturizer1 = new Moisturizer("Natural Moisturizer",
                "Coconut oil moisturizer", "Coconut oil, aloe vera",
                (AMOUNT_NEEDED_FOR_DISCOUNT/(1 - DISCOUNT)) + 0.02);
        shopper.setMaxPrice((AMOUNT_NEEDED_FOR_DISCOUNT/(1 - DISCOUNT)) + 1);
        assertTrue(shoppingCart.addProduct(moisturizer1));
        assertTrue(shoppingCart.checkForDiscount());
    }

}
