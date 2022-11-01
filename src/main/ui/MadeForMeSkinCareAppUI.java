package ui;

import model.*;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


//RFERENCE: alarm system
public class MadeForMeSkinCareAppUI extends JFrame implements ListSelectionListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private JComboBox<String> skinCombo;
    private JComboBox<String> concernCombo;
    private final JDesktopPane desktop;
    private final JInternalFrame startFrame;
    private final JInternalFrame shoppingCartFrame;
    private JTextField shopperName;
    private JList recommendationList;
    private DefaultListModel recommendationListModel;
    private Shopper shopper;
    private ShoppingCart shoppingCart;
    private List<Product> listOfOrdinaryProducts;

    private JButton viewButton;
    private JButton addButton;
    private JButton submitButton;
    private static final String viewString = "View Info";
    private static final String addString = "Add To Cart";
    private static final String submitString = "Submit";

    private static final String SKIN_TYPE_OILY = "oily";
    private static final String SKIN_TYPE_COMBO = "combination";
    private static final String SKIN_TYPE_DRY = "dry";

    private static final String CONCERN_TYPE_ACNE = "acne";
    private static final String CONCERN_TYPE_DRYNESS = "dryness";
    private static final String CONCERN_TYPE_HYPERPIGMENTATION = "hyperpigmentation";
    private static final String CONCERN_TYPE_REDNESS = "redness";


    public MadeForMeSkinCareAppUI() {
        setup();

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
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

    public void setup() {
        shopper = new Shopper();
        shoppingCart = new ShoppingCart(shopper);
        TheOrdinaryProducts theOrdinaryProducts = new TheOrdinaryProducts();
        listOfOrdinaryProducts = theOrdinaryProducts.getListOfTheOrdinaryProducts();
    }

    //EFFECTS: adds questionnaire panel to desktop on north
    private void addQuestionnairePanel() {
        JPanel questionnairePanel = new JPanel();
        questionnairePanel.setLayout(new GridLayout(3, 1));

        shopperName = new JTextField("Enter name", 10);
        questionnairePanel.add(shopperName);
        questionnairePanel.add(createSkinCombo());
        questionnairePanel.add(createConcernCombo());
        submitButton = new JButton(submitString);
        submitButton.setActionCommand(submitString);
        submitButton.addActionListener(new SubmitAnswersAction());
        questionnairePanel.add(submitButton);

        startFrame.add(questionnairePanel, BorderLayout.PAGE_START);
    }

    //EFFECTS: adds recommendation panel to start panel's internal frame
    private void addRecommendationPanel() {
        JPanel recommendationPanel = new JPanel();
        recommendationListModel = new DefaultListModel();
        if (shoppingCart.getRecommendationList().isEmpty()) {
            recommendationListModel.addElement("Recommendation List is empty!");
        } else {
            for (Product p : shoppingCart.getRecommendationList()) {
                recommendationListModel.addElement(p.getProductName());
            }
        }

        recommendationList = new JList(recommendationListModel);
        recommendationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recommendationList.setSelectedIndex(0);
        recommendationList.addListSelectionListener(this);
        recommendationList.setLayoutOrientation(JList.VERTICAL);
        recommendationList.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(recommendationList);

        addRecommendationListButtons(recommendationPanel);

        startFrame.add(recommendationPanel, BorderLayout.EAST);
        startFrame.add(listScrollPane, BorderLayout.PAGE_END);
    }

    public JPanel addRecommendationListButtons(JPanel recommendationPanel) {
        //ADD BUTTON
        addButton = new JButton(addString);
        addButton.setActionCommand(addString);
        addButton.addActionListener(new AddProduct());

        //VIEW BUTTON
        viewButton = new JButton(viewString);
        viewButton.setActionCommand(viewString);
        viewButton.addActionListener(new ViewProduct());

        recommendationPanel.add(addButton);
        recommendationPanel.add(Box.createHorizontalStrut(5));
        recommendationPanel.add(viewButton);
        recommendationPanel.add(Box.createHorizontalStrut(5));
        recommendationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return recommendationPanel;
    }


    private void addShoppingCartPanel() {
        ShoppingCartPanel scUI = new ShoppingCartPanel(shoppingCart, this);
        desktop.add(scUI);
    }

    @Override
    //TODO: what does this do?
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (recommendationList.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                viewButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                viewButton.setEnabled(true);
            }
        }
    }


    class ViewProduct implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = recommendationList.getSelectedIndex();
            //GET INDEX DETAILS
            //TODO: add an icon
            JOptionPane.showMessageDialog(null,
                    "\n" + shoppingCart.getRecommendationList().get(index).getProductName()
                            + ":" + shoppingCart.getRecommendationList().get(index).getDescription()
                            + "Ingredient Lists: " + shoppingCart.getRecommendationList().get(index).getIngredients()
                            + "Priced at $%.2f%n" + shoppingCart.getRecommendationList().get(index).getPrice(),
                    "Product Information",
                    JOptionPane.INFORMATION_MESSAGE);

            int size = recommendationListModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                viewButton.setEnabled(false);

            }

            recommendationList.setSelectedIndex(index);
            recommendationList.ensureIndexIsVisible(index);
        }
    }

    class AddProduct implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = recommendationList.getSelectedIndex();
            recommendationListModel.remove(index);

            int size = recommendationListModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                addButton.setEnabled(false);

            } else { //Select an index.
                if (index == recommendationListModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                recommendationList.setSelectedIndex(index);
                recommendationList.ensureIndexIsVisible(index);
            }
        }
    }

    private class SubmitAnswersAction extends AbstractAction {

        SubmitAnswersAction() {
            super("Submit Responses");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String selectedSkinType = (String) skinCombo.getSelectedItem();
            String selectedConcernType = (String) concernCombo.getSelectedItem();
            try {
                //TODO: how to do this
                saveSkinTypeAnswers(selectedSkinType);
                saveConcernAnswers(selectedConcernType);
                saveUsersName(shopperName.getText());
            } catch (LoginException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //EFFECTS: sets the users selected skin type option as shopper's skin type
    private void saveSkinTypeAnswers(String selected) throws LoginException {
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

    //EFFECTS: sets the users selected concern type option as shopper's concern type
    private void saveConcernAnswers(String selected) throws LoginException {
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

    //EFFECTS: sets the users input into textField as the shopper's name
    private void saveUsersName(String input) throws LoginException {
        if (!(input == null)) {
            shoppingCart.getShopper().setName(input);
        }
    }

    //EFFECTS: creates a combo box with skin type options: oily, dry, and combo.
    private JComboBox<String> createSkinCombo() {
        skinCombo = new JComboBox<String>();
        skinCombo.addItem(SKIN_TYPE_OILY);
        skinCombo.addItem(SKIN_TYPE_DRY);
        skinCombo.addItem(SKIN_TYPE_COMBO);
        return skinCombo;
    }

    //EFFECTS: creates a combo box with concern options: acne, hyperpigmentation, dryness and redness.
    private JComboBox<String> createConcernCombo() {
        concernCombo = new JComboBox<String>();
        concernCombo.addItem(CONCERN_TYPE_ACNE);
        concernCombo.addItem(CONCERN_TYPE_HYPERPIGMENTATION);
        concernCombo.addItem(CONCERN_TYPE_DRYNESS);
        concernCombo.addItem(CONCERN_TYPE_REDNESS);
        return concernCombo;
    }

    //Centres frame on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    /**
     * Represents action to be taken when user clicks desktop
     * to switch focus. (Needed for key handling.)
     */
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            MadeForMeSkinCareAppUI.this.requestFocusInWindow();
        }
    }

    // starts the application
    public static void main(String[] args) {
        new MadeForMeSkinCareAppUI();
    }


}
