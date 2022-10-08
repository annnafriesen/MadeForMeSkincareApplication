package ui;

import model.*;

import java.lang.module.FindException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// MadeForMe SkinCare Application
public class MadeForMeSkinCareApp {
    private Shopper shopper;
    private ShoppingCart shoppingCart;
    private TheOrdinaryProducts theOrdinaryProducts;
    private List<Product> listOfOrdinaryProducts;
    private Scanner input;

    //EFFECTS: runs the MadeForMe Skin Care app
    public MadeForMeSkinCareApp() {
        runMadeForMeApp();
    }

    //REFERENCE LIST: the following code mimics behaviour seen in TellerApp project provided in CPSC 210,
    // which can be found at https://github.students.cs.ubc.ca/CPSC210/TellerApp.git.

    //MODIFIES: this
    //EFFECTS: processes users information
    private void runMadeForMeApp() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            startHomeScreen();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                startQuestionnaire();
            }
        }
        System.out.println("Thank you for using the MadeForMe SkinCare App. "
                + "Next time turn that wishlist into reality!");
    }

    // MODIFIES: this
    // EFFECTS: initializes shopper and shopping cart objects
    private void init() {
        theOrdinaryProducts = new TheOrdinaryProducts();
        listOfOrdinaryProducts = theOrdinaryProducts.getListOfTheOrdinaryProducts();
        shopper = new Shopper();
        shoppingCart = new ShoppingCart(shopper);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addToCart();
        } else if (command.equals("r")) {
            removeFromCart();
        } else if (command.equals("w")) {
            printWishList();
        } else if (command.equals("c")) {
            printShoppingCart();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of skin type options to user
    private void displaySkinTypeOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\to -> Oily");
        System.out.println("\tc -> Combination");
        System.out.println("\td -> Dry");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays menu of concern type options to user
    private void displayConcernTypeOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Acne");
        System.out.println("\ty -> Dryness");
        System.out.println("\th -> Hyperpigmentation");
        System.out.println("\tr -> Redness");
        System.out.println("\tq -> quit");
    }


    //MODIFIES: this
    //EFFECTS: asks the shopper questions
    private void startHomeScreen() {
        System.out.println("Welcome to MadeForMe! Please enter your name:");
        String name = input.nextLine();
        shopper = new Shopper();
        shopper.setName(name);

        System.out.println("Hello, " + shopper.getCustomerName() + "! Answer the following questions to help us find "
                + "the best products for you.");
    }

    //MODIFIES: this
    //EFFECTS: asks the shopper questions
    //TODO: should this be done this way? too long?
    private void startQuestionnaire() {
        String command;
        command = input.next();
        command = command.toLowerCase();
        System.out.println("Please select your skin type:\n");
        displaySkinTypeOptions();
        String skinType = input.nextLine();
        setSkinType(skinType);
        System.out.println("Please select your max price range for the total cost of products:\n");
        Double maxPrice = input.nextDouble();
        setMaxPrice(maxPrice);
        System.out.println("Please select skin concerns you would like addressed:\n");
        displayConcernTypeOptions();
        String concernType = input.nextLine();
        setConcernType(concernType);
        System.out.println("Creating personalized skin care routine...:\n");
        createRecommendations();
        printRecommendationList();
        processCommand(command);
    }

    //MODIFIES: this
    //EFFECTS: sets the users skin type to oily, combo or dry
    private void setSkinType(String skinType) {
        switch (skinType) {
            case "o":
                shopper.setSkinType(SkinType.OILY);
                break;
            case "c":
                shopper.setSkinType(SkinType.COMBINATION);
                break;
            case "d":
                shopper.setSkinType(SkinType.DRY);
                break;
            default:
                System.out.println("Please select valid option.");
                break;
        }
    }


    //MODIFIES: this
    //EFFECTS: sets the users maxPrice
    private void setMaxPrice(Double maxPrice) {
        if (maxPrice > 0.0) {
            shopper.setMaxPrice(maxPrice);
        } else {
            System.out.println("Max price must be at least one cent!\n");
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the users skin type to oily, combo or dry
    private void setConcernType(String concernType) {
        switch (concernType) {
            case "a":
                shopper.setConcerns(ConcernType.ACNE);
                break;
            case "h":
                shopper.setConcerns(ConcernType.HYPERPIGMENTATION);
                break;
            case "d":
                shopper.setConcerns(ConcernType.DRYNESS);
                break;
            case "r":
                shopper.setConcerns(ConcernType.REDNESS);
                break;
            default:
                System.out.println("Please select valid option.");
                break;
        }
    }

    //EFFECTS: shows recommendations for the Ordinary Products based on users answers, and adds products starting
    // from most recommended to least recommended (always recommends moisturizer and cleanser)
    private void createRecommendations() {
        List<ConcernType> concerns = shopper.getConcerns();
        if (concerns.contains(ConcernType.ACNE)) {
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(6));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(8));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(1));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(4));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(0));
        } else if (concerns.contains(ConcernType.DRYNESS)) {
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(5));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(0));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(8));
        } else if (concerns.contains(ConcernType.HYPERPIGMENTATION)) {
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(2));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(6));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(8));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(0));
        } else if (concerns.contains(ConcernType.REDNESS)) {
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(7));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(3));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(0));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(8));
        }

    }

    //EFFECTS: prints recommendation list in format # -> ProductName
    public void printRecommendationList() {
        for (Product p : shoppingCart.getRecommendationList()) {
            int index = shoppingCart.getRecommendationList().indexOf(p);
            System.out.println(index + "-> " + p.getProductName());
        }
    }

    //EFFECTS: asks user to add products to cart
    private void addToCart() {
        //TODO: is this right?
        int productNumber = input.nextInt();
        shoppingCart.addProductToCart(shoppingCart.getRecommendationList().get(productNumber));
    }

    //EFFECTS: asks user to add products to cart
    private void removeFromCart() {
        for (Product p : shoppingCart.getProductsInCart()) {
            int index = shoppingCart.getProductsInCart().indexOf(p);
            System.out.println(index + "-> " + p.getProductName());
            int productNumber = input.nextInt();
            shoppingCart.removeProductFromCart(shoppingCart.getProductsInCart().get(productNumber));
        }
    }

    //EFFECTS: prints shopping cart
    private void printShoppingCart() {
        for (Product p : shoppingCart.getRecommendationList()) {
            System.out.println(p.getProductName());
        }
    }

    //EFFECTS: prints wish list
    private void printWishList() {
        for (Product p : shoppingCart.getWishList()) {
            System.out.println(p.getProductName());
        }
    }
}