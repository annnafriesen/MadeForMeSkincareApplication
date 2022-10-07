package ui;

import model.*;

import java.lang.module.FindException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

// MadeForMe SkinCare Application
public class MadeForMeSkinCareApp {
    private Shopper shopper;
    private ShoppingCart shoppingCart;
    private TheOrdinaryProducts productSelection;
    private Scanner input;
    private static final String OILY_SKIN_TYPE_COMMAND = "Oily";
    private static final String COMBO_SKIN_TYPE_COMMAND = "Combination";
    private static final String DRY_SKIN_TYPE_COMMAND = "Dry";

    //EFFECTS: runs the MadeForMe Skin Care app
    public MadeForMeSkinCareApp() {
        productSelection = new TheOrdinaryProducts();
        productSelection.getTheOrdinaryProducts();
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
            startQuestionnaire();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Thank you for using the MadeForMe SkinCare App. "
                + "Next time turn that wishlist into reality!");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    //TODO: what to include here?
    private void processCommand(String command) {
        if (command.length() > 0) {
            switch (command) {
                case ("c"):
                    printShoppingCart();
                case ("w"):
                    printWishList();
                case ("r"):
                    removeFromCart();
                case ("q"):
                    keepGoing = false;
                default:
                    System.out.println("Sorry, that is not a valid request. Please try again.");
                    break;
            }
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

    // MODIFIES: this
    // EFFECTS: initializes shopper and shopping cart objects
    private void init() {
        shopper = new Shopper();
        shoppingCart = new ShoppingCart(shopper);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //MODIFIES: this
    //EFFECTS: asks the shopper questions
    private void startQuestionnaire() {
        System.out.println("Welcome to MadeForMe! Please enter your name:");
        String name = input.nextLine();
        shopper = new Shopper();
        shopper.setName(name);

        System.out.println("Hello, " + shopper.getCustomerName() + "! Answer the following questions to help us find "
                + "the best products for you.");
        questionnaire();
    }

    //MODIFIES: this
    //EFFECTS: asks the shopper questions
    //TODO: should this be done this way? too long?
    private void questionnaire() {
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
        showRecommendations();
        addToCart();
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
                questionnaire();
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

    //EFFECTS: shows recommendations for the Ordinary Products based on users answers
    private void showRecommendations() {
        //TODO: how to get things instantiated in theOrdinaryProducts class???
        List<ConcernType> concerns = shopper.getConcerns();
        if (concerns.contains(ConcernType.ACNE)) {
            shoppingCart.addProductToRecommendationList(natural moisturizer);
            shoppingCart.addProductToRecommendationList();
        }
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