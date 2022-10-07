package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void testSetConcernsFirstConcern() {
        shopper.setConcerns(ConcernType.ACNE);
        assertTrue(shopper.getConcerns().contains(ConcernType.ACNE));
        assertEquals(1, shopper.getConcerns().size());
    }

    @Test
    void testSetConcernsSecondConcern() {
        shopper.setConcerns(ConcernType.ACNE);
        assertTrue(shopper.getConcerns().contains(ConcernType.ACNE));
        assertEquals(1, shopper.getConcerns().size());

        shopper.setConcerns(ConcernType.HYPERPIGMENTATION);
        assertTrue(shopper.getConcerns().contains(ConcernType.HYPERPIGMENTATION));
        assertTrue(shopper.getConcerns().contains(ConcernType.ACNE));
        assertEquals(2, shopper.getConcerns().size());
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
