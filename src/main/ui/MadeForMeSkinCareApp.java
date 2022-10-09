package ui;

import model.*;

import java.util.List;
import java.util.Scanner;

// MadeForMe SkinCare Application
public class MadeForMeSkinCareApp {
    private Shopper shopper;
    private ShoppingCart shoppingCart;
    private List<Product> listOfOrdinaryProducts;
    private Scanner input;

    private static final String ADD_COMMAND = "'add'";
    private static final String REMOVE_COMMAND = "'remove'";
    private static final String RECOMMENDATION_COMMAND = "'back'";
    private static final String WISHLIST_COMMAND = "'wishlist'";
    private static final String VIEW_CART_COMMAND = "'shopping cart'";

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
        startHomeScreen();

        while (keepGoing) {
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
    // EFFECTS: initializes shopper and shopping cart objects
    private void init() {
        TheOrdinaryProducts theOrdinaryProducts = new TheOrdinaryProducts();
        listOfOrdinaryProducts = theOrdinaryProducts.getListOfTheOrdinaryProducts();
        shopper = new Shopper();
        shoppingCart = new ShoppingCart(shopper);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    //TODO: too long
    private void processCommand(String command) {
        switch (command) {
            case "o":
            case "c":
            case "d":
                setSkinType(command);
                break;
            case "a":
            case "h":
            case "dr":
            case "r":
                setConcernType(command);
                break;
            case REMOVE_COMMAND:
                removeFromCart();
                break;
            case WISHLIST_COMMAND:
                viewWishList();
                break;
            case VIEW_CART_COMMAND:
                viewShoppingCart();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // EFFECTS: displays menu of skin type options to user
    private void displaySkinTypeOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\to -> Oily");
        System.out.println("\tc -> Combination");
        System.out.println("\td -> Dry");
        System.out.println("\tq -> Quit questionnaire");
    }

    // EFFECTS: displays menu of concern type options to user
    private void displayConcernTypeOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Acne");
        System.out.println("\tdr -> Dryness");
        System.out.println("\th -> Hyperpigmentation");
        System.out.println("\tr -> Redness");
        System.out.println("\tq -> Quit questionnaire");
    }

    // EFFECTS: displays menu of options to user
    private void displayListOptions() {
        System.out.println("Type " + RECOMMENDATION_COMMAND + " to view recommendation list");
        System.out.println("Type " + WISHLIST_COMMAND + " to view wishlist");
        System.out.println("Type " + VIEW_CART_COMMAND + " to view your shopping cart");
    }


    //MODIFIES: this
    //EFFECTS: asks the shopper questions
    private void startHomeScreen() {
        System.out.println("Welcome to MadeForMe SkinCare! Please enter your name:");
        String name = input.nextLine();
        shopper = new Shopper();
        shopper.setName(name);

        System.out.println("Hello, " + shopper.getCustomerName() + "! Answer the following questions to help us find "
                + "the best products for you. Press any key to continue.");
        startQuestionnaire();
    }

    //MODIFIES: this
    //EFFECTS: asks the shopper questions
    private void startQuestionnaire() {
        String command;
        command = input.next();
        command = command.toLowerCase();
        System.out.println("Please select your skin type:");
        displaySkinTypeOptions();
    }

    private void setSkinType(String command) {
        switch (command) {
            case ("o"):
                shopper.setSkinType(SkinType.OILY);
            case ("d"):
                shopper.setSkinType(SkinType.DRY);
            case ("c"):
                shopper.setSkinType(SkinType.COMBINATION);
        }

        System.out.println("Please select your max price range for the total cost of products:\n");
        Double maxPrice = input.nextDouble();
        setMaxPrice(maxPrice);
    }


    //MODIFIES: this
    //EFFECTS: sets the users maxPrice
    private void setMaxPrice(Double maxPrice) {
        if (maxPrice > 0.0) {
            shopper.setMaxPrice(maxPrice);
        } else {
            System.out.println("Max price must be at least one cent!\n");
        }

        System.out.println("Please select skin concerns you would like addressed:");
        displayConcernTypeOptions();
    }

    //MODIFIES: this
    //EFFECTS: sets the users skin type to oily, combo or dry
    private void setConcernType(String command) {
        switch (command) {
            case "a":
                shopper.setConcern(ConcernType.ACNE);
                break;
            case "h":
                shopper.setConcern(ConcernType.HYPERPIGMENTATION);
                break;
            case "dr":
                shopper.setConcern(ConcernType.DRYNESS);
                break;
            case "r":
                shopper.setConcern(ConcernType.REDNESS);
                break;
            default:
                System.out.println("Please select valid option.");
                break;
        }

        System.out.println("Creating personalized skin care routine...");
        System.out.println("Here are your results!");
        createRecommendations();
    }

    //EFFECTS: shows recommendations for the Ordinary Products based on users answers, and adds products starting
    // from most recommended to least recommended (always recommends moisturizer and cleanser)
    private void createRecommendations() {
        ConcernType concern = shopper.getConcern();
        if (concern.equals(ConcernType.ACNE)) {
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(6));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(8));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(1));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(4));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(0));
        } else if (concern.equals(ConcernType.DRYNESS)) {
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(5));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(0));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(8));
        } else if (concern.equals(ConcernType.HYPERPIGMENTATION)) {
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(2));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(6));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(8));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(0));
        } else if (concern.equals(ConcernType.REDNESS)) {
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(7));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(3));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(0));
            shoppingCart.addProductToRecommendationList(listOfOrdinaryProducts.get(8));
        }

        printRecommendationList();
    }

    //EFFECTS: prints recommendation list in format # -> ProductName
    public void printRecommendationList() {
        for (Product p : shoppingCart.getRecommendationList()) {
            int index = shoppingCart.getRecommendationList().indexOf(p);
            System.out.println((index + 1) + "." + p.getProductName());
        }
        System.out.println("To read the description, get ingredient list, or add a product, please type the "
                + "product number below.");
        viewProductDetails();
    }


    //EFFECTS: allows user to access description, ingredients and price of product and then gives them
    // the option to add the product to their cart.
    public void viewProductDetails() {
        //TODO: is there a way to make command type a int?
        int productNumber = input.nextInt();
        Product product = shoppingCart.getRecommendationList().get(productNumber - 1);
        System.out.println("\n" + product.getProductName() + ":");
        System.out.println(product.getDescription());
        System.out.println("Ingredient Lists: " + product.getIngredients());
        System.out.println("Priced at $" + product.getPrice());
        System.out.println("To add this product to your cart, type " + ADD_COMMAND);
        System.out.println("To return to your recommendation list, type 'back'");

        if (input.next().equals("add")) {
            addToCart(productNumber - 1);
        } else {
            if (input.next().equals("back")) {
                printRecommendationList();
            }
        }
    }

    //EFFECTS: tries to add product to shopping cart, and tells user if successful or not
    private void addToCart(int productNumber) {
        //TODO: how to add to cart?? Not working
        if (shoppingCart.addProductToCart(shoppingCart.getRecommendationList().get(productNumber))) {
            System.out.println("The product was added to your shopping cart!");
        } else {
            System.out.println("Product cannot be added to cart as price limit is reached, but it has been added to "
                    + "your wish list.");
        }
        displayListOptions();
    }

    //EFFECTS: asks user to add products to cart
    private void removeFromCart() {
        int productNumber = input.nextInt();
        shoppingCart.removeProductFromCart(shoppingCart.getProductsInCart().get(productNumber));
    }

    //EFFECTS: prints shopping cart
    private void viewShoppingCart() {
        for (Product p : shoppingCart.getRecommendationList()) {
            int index = shoppingCart.getProductsInCart().indexOf(p);
            System.out.println(index + "-> " + p.getProductName());
        }

        System.out.println("To remove a product, type 'remove'");
        if (input.next().equals("remove")) {
            removeFromCart();
        }
        displayListOptions();
    }

    //EFFECTS: prints wish list
    private void viewWishList() {
        for (Product p : shoppingCart.getWishList()) {
            System.out.println(p.getProductName());
        }
    }
}