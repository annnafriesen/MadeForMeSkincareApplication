package model;

import model.types.ConcernType;
import model.types.SkinType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Tests methods in the Shopper class
public class ShopperTest {
    private Shopper shopper;

    @BeforeEach
    public void setup() {
        shopper = new Shopper();
        shopper.setName("Rowan");
        shopper.setSkinType(SkinType.OILY);
        shopper.setMaxPrice(30);

    }

    @Test
    void testSetName() {
        shopper.setName("May");
        assertEquals("May", shopper.getCustomerName());
    }

    @Test
    void testSetConcern() {
        shopper.setConcern(ConcernType.ACNE);
        assertEquals(ConcernType.ACNE, shopper.getConcern());
    }

    @Test
    void testSetSkinTypeOnce() {
        assertEquals(SkinType.OILY, shopper.getSkinType());
        shopper.setSkinType(SkinType.DRY);
        assertEquals(SkinType.DRY, shopper.getSkinType());
    }

    @Test
    void testSetSkinTypeTwice() {
        assertEquals(SkinType.OILY, shopper.getSkinType());
        shopper.setSkinType(SkinType.DRY);
        assertEquals(SkinType.DRY, shopper.getSkinType());

        shopper.setSkinType(SkinType.OILY);
        assertEquals(SkinType.OILY, shopper.getSkinType());
    }

    @Test
    void testMaxPrice() {
        assertEquals(30, shopper.getMaxPrice());
        shopper.setMaxPrice(50);
        assertEquals(50, shopper.getMaxPrice());
    }
}
