package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TheOrdinaryProductsTest {
    TheOrdinaryProducts productSelection;

    @BeforeEach
    public void setup() {
        productSelection = new TheOrdinaryProducts();
    }

    @Test
    void testGetListOfTheOrdinaryProducts() {
        List<Product> list = productSelection.getListOfTheOrdinaryProducts();
        assertEquals(9, list.size());
        assertTrue(list.contains(productSelection.naturalMoisturizer));
        assertTrue(list.contains(productSelection.salicylicAcid2Percent));
        assertTrue(list.contains(productSelection.lacticAcid5Percent));
        assertTrue(list.contains(productSelection.mandelicAcid10Percent));
        assertTrue(list.contains(productSelection.niacinamide10Percent));
        assertTrue(list.contains(productSelection.hyaluronicAcid2Percent));
        assertTrue(list.contains(productSelection.retinol1Percent));
        assertTrue(list.contains(productSelection.azelaicAcidSuspension10Percent));
        assertTrue(list.contains(productSelection.squalanceCleanser));
    }
}
