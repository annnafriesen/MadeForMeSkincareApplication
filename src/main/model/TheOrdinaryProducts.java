package model;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.List;

// Instantiates the Ordinary Products that are for sale; includes moisturizers, exfoliants, serums, and cleansers.
// Each product has a name, description, ingredient list and price attached to it.
public class TheOrdinaryProducts {
    //MOISTURIZERS
    //TODO: how to make sure 0 doesn't disappear when calling price?
    Product naturalMoisturizer = new Moisturizer("Natural Moisturizer", "Keeps the outer layer "
            + "of your skin protected and well-hydrated, without feeling greasy.", "Sodium Hyaluronate, "
            + "Arginine, Sodium PCA, PCA, Lactates, Lactic Acid, and Minerals", 11.50);

    //EXFOLIANTS
    Product salicylicAcid2Percent = new Exfoliator("Salicylic Acid 2% Solution", "A targeted "
            + "serum for blemish-prone skin.", "Salcylic Acid, Saccharide Isomerate", 6.50);

    Product lacticAcid5Percent = new Exfoliator("Lactic Acid 5%", "A milder exfoliator that "
            + "works to reveal smoother skin.", "Lactic Acid, Sodium Hyaluronate Crosspolymer, "
            + "Tasmannia Lanceolata Fruit/Leaf Extract", 7.70);

    Product mandelicAcid10Percent = new Exfoliator("Mandelic Acid 10%", "A gentler exfoliant, "
            + "in serum form.", "Mandelic Acid, Sodium Hyaluronate Crosspolymer, Tasmannia "
            + "Lanceolata Fruit/Leaf Extract", 8.80);


    //SERUMS
    Product niacinamide10Percent = new Serum("Niacinamide 10% and Zinc 1%", "A universal "
            + "serum for blemish-prone skin that smooths brightens, and supports skin health", "Niacinamide,"
            + "Zinc PCA", 12.80);

    Product hyaluronicAcid2Percent = new Serum("Hyaluronic Acid 2% + B5", "A hydrator "
            + "that smooths and plumps with hyaluronic acid.", "Sodium Hyaluronate,"
            + " Sodium Hyaluronate Crosspolymer, Panthenol", 14.70);

    Product retinol1Percent = new Serum("Retinol 1%", "A high-strength retinol serum "
            + "that targets general signs of skin aging and acne.", "Retinol, Squalane", 8.80);

    Product azelaicAcidSuspension10Percent = new Serum("Azelaic Acid Suspension 10%", "A "
            + "formula for uneven and blemish-prone skin.", "Azelaic Acid", 27.20);

    //CLEANSERS
    Product squalanceCleanser = new Cleanser("Squalance Cleanser", "A moisturizing all-in-one "
            + "cleanser", "Squalane, Sucrose Stearate, Ethyl Macadamiate, Sucrose Laurate", 21.70);


    public List<Product> getListOfTheOrdinaryProducts() {
        List<Product> listOfTheOrdinaryProducts = new ArrayList<>();
        listOfTheOrdinaryProducts.add(naturalMoisturizer); //0
        listOfTheOrdinaryProducts.add(salicylicAcid2Percent); //1
        listOfTheOrdinaryProducts.add(lacticAcid5Percent); //2
        listOfTheOrdinaryProducts.add(mandelicAcid10Percent); //3
        listOfTheOrdinaryProducts.add(niacinamide10Percent); //4
        listOfTheOrdinaryProducts.add(hyaluronicAcid2Percent); //5
        listOfTheOrdinaryProducts.add(retinol1Percent); //6
        listOfTheOrdinaryProducts.add(azelaicAcidSuspension10Percent); //7
        listOfTheOrdinaryProducts.add(squalanceCleanser); //8
        return listOfTheOrdinaryProducts;
    }

}
