package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static model.ShoppingCart.AMOUNT_NEEDED_FOR_DISCOUNT;
import static model.ShoppingCart.DISCOUNT;

//REFERENCE LIST: the following code mimics behaviour seen in TellerApp project provided in CPSC 210,
// which can be found at https://github.students.cs.ubc.ca/CPSC210/TellerApp.git. I referenced the TellerApp when
// designing my ui, especially when creating the init() and runMadeForMeApp(). As well, I tried to create a similar
// flow to the TellerApp by having a general processCommand() method.
// Additionally, I referenced the FitLifeGymKiosk Project provided in the CPSC 210 repository, which is found at
//  https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/tree/main/FitLifeGymKiosk. I modelled my program
// to have local variables for each command, as seen in FitLifeGymKiosk as well as the use of switch statements.

// MadeForMe SkinCare Application
public class MadeForMeSkinCareApp {
    private Shopper shopper;
    private ShoppingCart shoppingCart;
    private List<Product> listOfOrdinaryProducts;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/shoppingCart.json";

    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String RECOMMENDATION_COMMAND = "back";
    private static final String WISHLIST_COMMAND = "wl";
    private static final String VIEW_CART_COMMAND = "sc";
    private static final String VIEW_CHECKOUT = "done";
    private static final String SAVE_COMMAND = "s";
    private static final String LOAD_COMMAND = "l";


    //EFFECTS: runs the MadeForMe Skin Care app
    public MadeForMeSkinCareApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runMadeForMeApp();
    }

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
                processCommandPart1(command);
            }
        }
        System.out.println("Thank you for using the MadeForMe SkinCare App to start your skin care journey. "
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
    // EFFECTS: processes the first half of user commands
    private void processCommandPart1(String command) {
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
            default:
                processCommandPart2(command);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes the second half of user commands
    private void processCommandPart2(String command) {
        switch (command) {
            case RECOMMENDATION_COMMAND:
                printRecommendationList();
                break;
            case WISHLIST_COMMAND:
                viewWishList();
                break;
            case VIEW_CART_COMMAND:
                viewShoppingCart();
                break;
            case VIEW_CHECKOUT:
                viewCheckout();
                break;
            case SAVE_COMMAND:
                saveShoppingCart();
                break;
            case LOAD_COMMAND:
                loadShoppingCart();
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

    // EFFECTS: displays menu when user is viewing a product, and gives them the choice to go to their recommendation
    // list, wishlist, shopping cart, or checkout
    private void displayProductLocationOptions() {
        System.out.println("Type '" + RECOMMENDATION_COMMAND + "' to view recommendation list");
        System.out.println("Type '" + WISHLIST_COMMAND + "' to view wishlist");
        System.out.println("Type '" + VIEW_CART_COMMAND + "' to view your shopping cart");
        System.out.println("Type '" + VIEW_CHECKOUT + "' to go to checkout");
        System.out.println("Type '" + SAVE_COMMAND + "'save shopping cart to file");
        System.out.println("Type '" + LOAD_COMMAND + "' load shopping cart from file");
    }

    // EFFECTS: displays menu when user is viewing their recommendation list
    private void displayRecommendationOptions() {
        System.out.println("\nType '" + ADD_COMMAND + "' to add product to your shopping cart");
        System.out.println("Type '" + RECOMMENDATION_COMMAND + "' to view recommendation list");
        System.out.println("Type '" + WISHLIST_COMMAND + "' to view wishlist");
        System.out.println("Type '" + VIEW_CART_COMMAND + "' to view your shopping cart");
        System.out.println("Type '" + LOAD_COMMAND + "' load shopping cart from file");
    }


    //MODIFIES: this
    //EFFECTS: displays MadeForMe SkinCare brand message and asks for users name
    private void startHomeScreen() {
        System.out.println("Welcome to MadeForMe SkinCare! Please enter your name:");
        String name = input.nextLine();
        shopper.setName(name);

        System.out.println("Hello, " + shopper.getCustomerName() + "! Answer the following questions to help us find "
                + "the best products for you. Press any key to continue.");
        startQuestionnaire();
    }

    //MODIFIES: this
    //EFFECTS: asks the shopper the first questionnaire question (what is your skin type?)
    private void startQuestionnaire() {
        String command = input.next();
        command = command.toLowerCase();
        System.out.println("Please select your skin type:");
        displaySkinTypeOptions();
    }

    //MODIFIES: this
    //EFFECTS: sets the users skin type
    private void setSkinType(String command) {
        switch (command) {
            case ("o"):
                shopper.setSkinType(SkinType.OILY);
                break;
            case ("d"):
                shopper.setSkinType(SkinType.DRY);
                break;
            case ("c"):
                shopper.setSkinType(SkinType.COMBINATION);
                break;
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
            System.out.println("Max price must be at least one cent! Please enter a valid price.\n");
            Double num = input.nextDouble();
            shopper.setMaxPrice(num);

        }

        System.out.println("Please select the main skin concern you would like addressed:");
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
    // the option to add the product to their cart or return to the recommendation list
    public void viewProductDetails() {
        int recommendationNumber = input.nextInt();
        Product product = shoppingCart.getRecommendationList().get(recommendationNumber - 1);
        System.out.println("\n" + product.getProductName() + ":");
        System.out.println(product.getDescription());
        System.out.println("Ingredient Lists: " + product.getIngredients());
        System.out.printf("Priced at $%.2f%n", product.getPrice());
        displayRecommendationOptions();

        String nextCommand = input.next();
        if (nextCommand.equals(ADD_COMMAND)) {
            addToCart(recommendationNumber - 1);

        } else {
            if (nextCommand.equals(RECOMMENDATION_COMMAND)) {
                printRecommendationList();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: tries to add product to shopping cart, and tells user if successful or not
    private void addToCart(int productNumber) {

        if (shoppingCart.addProductToCart(shoppingCart.getRecommendationList().get(productNumber))) {
            System.out.println("\nThe product was added to your shopping cart! If you spend more than $"
                    + AMOUNT_NEEDED_FOR_DISCOUNT + " then you receive a " + DISCOUNT * 100 + "% on your purchase!");
        } else {
            System.out.println("\nProduct cannot be added to cart as price limit is reached, but it has been added to "
                    + "your wish list.");
        }
        displayProductLocationOptions();
    }

    //MODIFIES: this
    //EFFECTS: asks user to remove products to cart by typing the product number into console
    private void removeFromCart() {
        System.out.println("\nplease enter the product number of the product you would like to remove:");
        int productNumber = input.nextInt();
        shoppingCart.removeProductFromCart(shoppingCart.getProductsInCart().get(productNumber - 1));
        System.out.println("The product has been removed from your cart.");
        displayProductLocationOptions();
    }

    //EFFECTS: prints shopping cart; if empty, tells user that cart is empty
    private void viewShoppingCart() {
        if (shoppingCart.getProductsInCart().isEmpty()) {
            System.out.println("\nShopping cart is currently empty.");
        }

        for (Product p : shoppingCart.getProductsInCart()) {
            int index = shoppingCart.getProductsInCart().indexOf(p);
            System.out.println("#" + (index + 1) + " " + p.getProductName());
        }

        System.out.printf("Total: $%.2f%n", shoppingCart.getTotalPrice());

        System.out.println("\nTo remove a product, type 'remove'.");
        displayProductLocationOptions();

    }

    //EFFECTS: prints wish list; if empty, tells user that the wish list is empty
    private void viewWishList() {
        if (shoppingCart.getWishList().isEmpty()) {
            System.out.println("Wish list is currently empty.");
        }

        System.out.println("\nYour wishlist contains:");
        for (Product p : shoppingCart.getWishList()) {
            System.out.println(p.getProductName());
        }
        displayProductLocationOptions();
    }

    //MODIFIES: this (if checkForDiscount is true)
    //EFFECTS: prints check out menu
    private void viewCheckout() {
        System.out.println("\nCart:");
        if (shoppingCart.getProductsInCart().isEmpty()) {
            System.out.println("Nothing here!");
        } else {
            for (Product p : shoppingCart.getProductsInCart()) {
                int index = shoppingCart.getProductsInCart().indexOf(p);
                System.out.println("#" + (index + 1) + " " + p.getProductName());
            }
        }

        System.out.printf("Cart total comes to $%.2f%n", shoppingCart.getTotalPrice());
        if (shoppingCart.checkForDiscount()) {
            System.out.println("You received a " + DISCOUNT * 100 + "% on your purchase!");
            System.out.printf("Your final total comes to $%.2f%n", shoppingCart.getTotalPrice());
        }

        System.out.println("\nPlease press 'q' to end MadeForMe program.");
    }

    // EFFECTS: saves the workroom to file
    private void saveShoppingCart() {
        try {
            jsonWriter.open();
            jsonWriter.write(shoppingCart);
            jsonWriter.close();
            System.out.println("Saved " + shoppingCart.getShopper().getCustomerName() + "'s Shopping Cart to "
                    + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        displayProductLocationOptions();
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadShoppingCart() {
        String name = shoppingCart.getShopper().getCustomerName();
        try {
            shoppingCart = jsonReader.read();
            System.out.println("Loaded " + name + "'s Shopping Cart from "
                    + JSON_STORE + "\n");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        displayProductLocationOptions();
    }
}