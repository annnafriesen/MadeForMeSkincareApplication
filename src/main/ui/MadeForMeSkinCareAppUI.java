package ui;

import model.*;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static model.ShoppingCart.AMOUNT_NEEDED_FOR_DISCOUNT;
import static model.ShoppingCart.DISCOUNT;


//REFERENCE LIST: the following code mimics behaviour seen in AlarmSystem project provided in CPSC 210,
// which can be found at https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git/. I referenced the AlarmSystem to
// learn how to add panels and buttons to my gui, as well as to learn how action events work.
// Additionally, I referenced the ListDemo Project provided in the Java tutorials on Oracle.com, which can be found at
// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https:
// docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java. I
// referenced this project to learn how to create a scroll panel for the recommendation list.
// See ShoppingCartUI class for copyright notice.

//Represents GUI of the MadeForMe SkinCare application.
public class MadeForMeSkinCareAppUI extends JFrame implements ListSelectionListener {
    public static final int WIDTH = 750;
    public static final int HEIGHT = 600;
    private JComboBox<String> skinCombo;
    private JComboBox<String> concernCombo;
    private final JDesktopPane desktop;
    private final JInternalFrame startFrame;
    private final JInternalFrame shoppingCartFrame;
    private final ImageIcon theOrdinaryLogo = new ImageIcon("model/theOrdinaryLogo.png");
    private final ImageIcon fullShoppingCartImage = new ImageIcon("model/FullShoppingCart.png");
    private JTextField shopperName;
    private JTextField maxPrice;
    private JList recommendationList;
    private DefaultListModel recommendationListModel;
    private JScrollPane listScrollPane;
    private ShoppingCartUI shoppingCartPanel;

    private Shopper shopper;
    private ShoppingCart shoppingCart;
    private List<Product> listOfOrdinaryProducts;

    private JButton viewButton;
    private JButton addButton;
    private JButton submitButton;
    private static final String viewString = "View Info";
    private static final String addString = "Add To Cart";
    private static final String submitString = "Submit";
    private JLabel nameLabel;
    private JLabel maxPriceLabel;
    private JLabel skinTypeLabel;
    private JLabel concernLabel;

    private static final String SKIN_TYPE_OILY = "oily";
    private static final String SKIN_TYPE_COMBO = "combination";
    private static final String SKIN_TYPE_DRY = "dry";

    private static final String DEFAULT_TYPE = "";
    private static final String CONCERN_TYPE_ACNE = "acne";
    private static final String CONCERN_TYPE_DRYNESS = "dryness";
    private static final String CONCERN_TYPE_HYPERPIGMENTATION = "hyperpigmentation";
    private static final String CONCERN_TYPE_REDNESS = "redness";

    //EFFECTS: Constructs GUI for the MadeForMe SkinCare app
    public MadeForMeSkinCareAppUI() {
        setup();

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        desktop.setBackground(Color.ORANGE);
        startFrame = new JInternalFrame("Questionnaire", false, false, false,
                false);
        startFrame.setLayout(new BorderLayout());
        shoppingCartFrame = new JInternalFrame("Shopping Cart", false, false, false,
                false);
        shoppingCartFrame.setLayout(new BorderLayout());

        setContentPane(desktop);
        setTitle("MadeForMe SkinCare");
        setSize(WIDTH, HEIGHT);

        addQuestionnairePanel();
        addRecommendationPanel();
        addShoppingCartPanel();

        startFrame.pack();
        startFrame.setVisible(true);
        desktop.add(startFrame);
        desktop.add(shoppingCartFrame);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: instantiates shopper, shopping cart and The Ordinary products
    public void setup() {
        shopper = new Shopper();
        shoppingCart = new ShoppingCart(shopper);
        TheOrdinaryProducts theOrdinaryProducts = new TheOrdinaryProducts();
        listOfOrdinaryProducts = theOrdinaryProducts.getListOfTheOrdinaryProducts();
    }

    //MODIFIES: this
    //EFFECTS: adds questionnaire panel to top of desktop; has fields and buttons for user input of name, max price,
    // skin type and concern type
    private void addQuestionnairePanel() {
        JPanel questionnairePanel = new JPanel();
        questionnairePanel.setLayout(new GridLayout(6, 1));
        setBorder(questionnairePanel);
        nameLabel = new JLabel("Enter name:", SwingConstants.RIGHT);
        questionnairePanel.add(nameLabel);
        nameLabel.setLabelFor(shopperName);
        shopperName = new JTextField(10);
        questionnairePanel.add(shopperName);

        maxPriceLabel = new JLabel("Enter max price:", SwingConstants.RIGHT);
        maxPriceLabel.setLabelFor(maxPrice);
        questionnairePanel.add(maxPriceLabel);
        maxPrice = new JTextField(7);
        questionnairePanel.add(maxPrice);

        addComboToPanel(questionnairePanel);

        submitButton = new JButton(submitString);
        submitButton.setActionCommand(submitString);
        submitButton.addActionListener(new SubmitAnswersAction());
        questionnairePanel.add(submitButton);
        startFrame.add(questionnairePanel, BorderLayout.NORTH);

    }

    //MODIFIES: this
    //EFFECTS: adds border to questionnaire panel, displaying the discount message
    public void setBorder(JPanel panel) {
        TitledBorder border = new TitledBorder("Receive a " + DISCOUNT * 100 + "% on orders over $"
                + AMOUNT_NEEDED_FOR_DISCOUNT + "0!");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        border.setTitleColor(Color.BLUE);
        panel.setBorder(border);
    }

    //MODIFIES: this
    //EFFECTS: creates combo boxes for skin type and concern type, each with labels
    private void addComboToPanel(JPanel questionnairePanel) {
        skinTypeLabel = new JLabel("Select skin type:", SwingConstants.RIGHT);
        skinTypeLabel.setLabelFor(createSkinCombo());
        questionnairePanel.add(skinTypeLabel);
        questionnairePanel.add(createSkinCombo());

        concernLabel = new JLabel("Select skin concern:", SwingConstants.RIGHT);
        concernLabel.setLabelFor(createConcernCombo());
        questionnairePanel.add(concernLabel);
        questionnairePanel.add(createConcernCombo());
    }

    //MODIFIES: this
    //EFFECTS: adds recommendation list panel to start panel's internal frame
    private void addRecommendationPanel() {
        JPanel recommendationPanel = new JPanel();
        recommendationListModel = new DefaultListModel();
        recommendationListModel.addElement("Recommendation List is empty!");

        recommendationList = new JList(recommendationListModel);
        recommendationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recommendationList.setSelectedIndex(0);
        recommendationList.addListSelectionListener(this);
        recommendationList.setLayoutOrientation(JList.VERTICAL);
        recommendationList.setVisibleRowCount(10);
        listScrollPane = new JScrollPane(recommendationList);

        startFrame.add(addRecommendationListButtons(recommendationPanel), BorderLayout.EAST);
        startFrame.add(listScrollPane, BorderLayout.PAGE_END);
    }

    //EFFECTS: adds the "add" and "view" buttons to the recommendation panel, and returns the updated recommendation
    // panel to be added to the frame
    public JPanel addRecommendationListButtons(JPanel recommendationPanel) {
        //ADD BUTTON
        addButton = new JButton(addString);
        addButton.setActionCommand(addString);
        addButton.addActionListener(new AddProduct());
        addButton.setEnabled(false);

        //VIEW BUTTON
        viewButton = new JButton(viewString);
        viewButton.setActionCommand(viewString);
        viewButton.addActionListener(new ViewProduct());
        viewButton.setEnabled(false);

        recommendationPanel.add(addButton);
        recommendationPanel.add(Box.createHorizontalStrut(5));
        recommendationPanel.add(viewButton);
        recommendationPanel.add(Box.createHorizontalStrut(5));
        recommendationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return recommendationPanel;
    }

    //MODIFIES: this
    //EFFECTS: adds the Shopping Cart panel to the desktop
    private void addShoppingCartPanel() {
        shoppingCartPanel = new ShoppingCartUI(shoppingCart, this);
        shoppingCartFrame.add(shoppingCartPanel);
        desktop.add(shoppingCartPanel);
    }


    @Override
    //TODO: what does this do?
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (recommendationList.getSelectedIndex() == -1) {
                viewButton.setEnabled(false);

            } else {
                viewButton.setEnabled(true);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: creates action for the view button; shows dialog box with product name, description, ingredient list,
    // and price
    class ViewProduct implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = recommendationList.getSelectedIndex();
            JOptionPane.showMessageDialog(null,
                    shoppingCart.getRecommendationList().get(index).getProductName()
                            + ": \n" + shoppingCart.getRecommendationList().get(index).getDescription()
                            + "\nIngredient Lists: " + shoppingCart.getRecommendationList().get(index).getIngredients()
                            + "\nPriced at $" + shoppingCart.getRecommendationList().get(index).getPrice() + "0",
                    "Product Information",
                    JOptionPane.INFORMATION_MESSAGE, theOrdinaryLogo);

            int size = recommendationListModel.getSize();

            if (size == 0) {
                viewButton.setEnabled(false);

            }

            recommendationList.setSelectedIndex(index);
            recommendationList.ensureIndexIsVisible(index);
        }
    }

    //MODIFIES: this
    //EFFECTS: Creates action for the add button. If max price range is met, dialog box pops up to alert user of limit
    // being reached
    class AddProduct implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = recommendationList.getSelectedIndex();
            int listIndex = shoppingCart.getRecommendationList().size() - 1;
            Product product = shoppingCart.getRecommendationList().get(listIndex - index);
            if (shoppingCart.addProductToCart(product)) {
                shoppingCartPanel.addProductToList(product);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Your cart has reached its max price range!",
                        "Price Limit Reached",
                        JOptionPane.INFORMATION_MESSAGE, fullShoppingCartImage);
            }

            recommendationList.setSelectedIndex(index);
            recommendationList.ensureIndexIsVisible(index);
        }
    }

    //MODIFIES: this
    //EFFECTS: creates action for the "submit" button. Tries to submit user's selected name, max price, skin type and
    // concern type; catches LoginException and NumberFormatException and displays dialog box for each to alert user.
    private class SubmitAnswersAction extends AbstractAction {

        SubmitAnswersAction() {
            super("Submit Responses");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String selectedSkinType = (String) skinCombo.getSelectedItem();
            String selectedConcernType = (String) concernCombo.getSelectedItem();
            try {
                saveUserInput(selectedSkinType, selectedConcernType);
                createRecommendationList();
                recommendationListModel.removeAllElements();
                for (Product p : shoppingCart.getRecommendationList()) {
                    int index = 0;
                    recommendationListModel.insertElementAt(p.getProductName(), index);
                    recommendationList.setSelectedIndex(index);
                    recommendationList.ensureIndexIsVisible(index);
                    index++;
                }
                addButtonVisibility();
            } catch (LoginException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number. Digits only,"
                                + "please!",
                        "System Error", JOptionPane.ERROR_MESSAGE);

            }
        }

        //MODIFIES: this
        //EFFECTS: saves users input into respective category: name, max price, skin type, and concern type
        private void saveUserInput(String selectedSkinType, String selectedConcernType) throws LoginException {
            saveSkinTypeAnswers(selectedSkinType);
            saveConcernAnswers(selectedConcernType);
            saveUsersName(shopperName.getText());
            saveMaxPrice(maxPrice.getText());
        }

        //MODIFIES: this
        //EFFECTS: sets the questionnaire panel's button visibility after submit button has been pressed; ensures
        // user cannot edit answers
        private void addButtonVisibility() {
            addButton.setEnabled(true);
            viewButton.setEnabled(true);
            shopperName.setEnabled(false);
            maxPrice.setEnabled(false);
            concernCombo.setEnabled(false);
            skinCombo.setEnabled(false);
            submitButton.setEnabled(false);
        }
    }

    //MODIFIES: this
    //EFFECTS: shows recommendations for the Ordinary Products based on users answers, and adds products starting
    // from most recommended to least recommended (always recommends moisturizer and cleanser)
    public void createRecommendationList() {
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

    }

    //MODIFIES: this
    //EFFECTS: sets the users selected skin type option as shopper's skin type
    // throws LoginException if no option is selected
    public void saveSkinTypeAnswers(String selected) throws LoginException {
        switch (selected) {
            case SKIN_TYPE_DRY:
                shoppingCart.getShopper().setSkinType(SkinType.DRY);
                break;
            case SKIN_TYPE_COMBO:
                shoppingCart.getShopper().setSkinType(SkinType.COMBINATION);
                break;
            case SKIN_TYPE_OILY:
                shoppingCart.getShopper().setSkinType(SkinType.OILY);
                break;
            default:
                throw new LoginException("Please select a skin type.");
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the users selected concern type option as shopper's concern type
    // throws LoginException if no option is selected
    public void saveConcernAnswers(String selected) throws LoginException {
        switch (selected) {
            case CONCERN_TYPE_ACNE:
                shoppingCart.getShopper().setConcern(ConcernType.ACNE);
                break;
            case CONCERN_TYPE_HYPERPIGMENTATION:
                shoppingCart.getShopper().setConcern(ConcernType.HYPERPIGMENTATION);
                break;
            case CONCERN_TYPE_REDNESS:
                shoppingCart.getShopper().setConcern(ConcernType.REDNESS);
                break;
            case CONCERN_TYPE_DRYNESS:
                shoppingCart.getShopper().setConcern(ConcernType.DRYNESS);
                break;
            default:
                throw new LoginException("Please select a concern type.");
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the users input into textField as the shopper's name
    // throws LoginException if no name is inputted
    private void saveUsersName(String input) throws LoginException {
        if (!(input == null)) {
            shoppingCart.getShopper().setName(input);
        } else {
            throw new LoginException();
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the users input into textField as the shopper's max price
    // throws NumberFormatException if input cannot be parsed to a double
    // throws LoginException if no option is selected
    private void saveMaxPrice(String input) throws LoginException, NumberFormatException {
        if (!(input == null)) {
            double price = Double.parseDouble(input);
            shoppingCart.getShopper().setMaxPrice(price);
        } else {
            throw new LoginException();
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a combo box with skin type options: oily, dry, and combo.
    private JComboBox<String> createSkinCombo() {
        skinCombo = new JComboBox<String>();
        skinCombo.addItem(DEFAULT_TYPE);
        skinCombo.addItem(SKIN_TYPE_OILY);
        skinCombo.addItem(SKIN_TYPE_DRY);
        skinCombo.addItem(SKIN_TYPE_COMBO);
        return skinCombo;
    }

    //MODIFIES: this
    //EFFECTS: creates a combo box with concern options: acne, hyperpigmentation, dryness and redness.
    private JComboBox<String> createConcernCombo() {
        concernCombo = new JComboBox<String>();
        concernCombo.addItem(DEFAULT_TYPE);
        concernCombo.addItem(CONCERN_TYPE_ACNE);
        concernCombo.addItem(CONCERN_TYPE_HYPERPIGMENTATION);
        concernCombo.addItem(CONCERN_TYPE_DRYNESS);
        concernCombo.addItem(CONCERN_TYPE_REDNESS);
        return concernCombo;
    }

    //MODIFIES: this
    //Centres frame on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }


    //EFFECTS: Represents action to be taken when user clicks desktop to switch focus.
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            MadeForMeSkinCareAppUI.this.requestFocusInWindow();
        }

    }

    //EFFECTS: starts the application
    public static void main(String[] args) {
        new MadeForMeSkinCareAppUI();
    }


}
