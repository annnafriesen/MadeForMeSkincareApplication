package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    Product moisturizer1;
    Product serum1;
    Product exfoliator1;
    Product cleanser1;

    @BeforeEach
    public void setup() {
        moisturizer1 = new Moisturizer("Moisturizer", "Gentle", "Water", 10.00);
        serum1 = new Serum("Serum", "Detoxifies", "Water", 11.00);
        exfoliator1 = new Exfoliator("Exfoliator", "Removes debris", "Water", 12.00);
        cleanser1 = new Cleanser("Cleanser", "Cleans", "Water", 13.00);
    }

    @Test
    void testMoisturizerGetters() {
        assertEquals("Moisturizer", moisturizer1.getProductType());
        assertEquals("Moisturizer", moisturizer1.getProductName());
        assertEquals("Gentle", moisturizer1.getDescription());
        assertEquals("Water", moisturizer1.getIngredients());
        assertEquals(10.00, moisturizer1.getPrice());
    }

    @Test
    void testSerumGetters() {
        assertEquals("Serum", serum1.getProductType());
        assertEquals("Serum", serum1.getProductName());
        assertEquals("Detoxifies", serum1.getDescription());
        assertEquals("Water", serum1.getIngredients());
        assertEquals(11.00, serum1.getPrice());
    }

    @Test
    void testExfoliatorGetters() {
        assertEquals("Exfoliator", exfoliator1.getProductType());
        assertEquals("Exfoliator", exfoliator1.getProductName());
        assertEquals("Removes debris", exfoliator1.getDescription());
        assertEquals("Water", exfoliator1.getIngredients());
        assertEquals(12.00, exfoliator1.getPrice());
    }

    @Test
    void testCleanserGetters() {
        assertEquals("Cleanser", cleanser1.getProductType());
        assertEquals("Cleanser", cleanser1.getProductName());
        assertEquals("Cleans", cleanser1.getDescription());
        assertEquals("Water", cleanser1.getIngredients());
        assertEquals(13.00, cleanser1.getPrice());
    }
}
