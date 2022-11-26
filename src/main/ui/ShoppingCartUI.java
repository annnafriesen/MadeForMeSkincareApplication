package ui;

import model.Product;
import model.Shopper;
import model.ShoppingCart;
import model.TheOrdinaryProducts;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// REFERENCES: the following code mimics behaviour seen in the ListDemo Project provided in the Java tutorials on
// Oracle.com, which can be found at https://docs.oracle.com/javase/tutorial/displayCode.html?code=https:
// docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java. I
// referenced this project to learn how to create a scroll panel for the shopping cart. Please refer to the copyright
// notice below.

//EFFECTS: creates the GUI for the shopping cart panel
public class ShoppingCartUI extends JInternalFrame implements ListSelectionListener {
    /*
     * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
     *
     * Redistribution and use in source and binary forms, with or without
     * modification, are permitted provided that the following conditions
     * are met:
     *
     *   - Redistributions of source code must retain the above copyright
     *     notice, this list of conditions and the following disclaimer.
     *
     *   - Redistributions in binary form must reproduce the above copyright
     *     notice, this list of conditions and the following disclaimer in the
     *     documentation and/or other materials provided with the distribution.
     *
     *   - Neither the name of Oracle or the names of its
     *     contributors may be used to endorse or promote products derived
     *     from this software without specific prior written permission.
     *
     * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
     * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
     * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
     * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
     * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
     * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
     * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
     * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
     * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
     * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
     * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
     */

    private JList cartList;
    private DefaultListModel cartListModel;

    private Shopper shopper;
    private ShoppingCart shoppingCart;
    private List<Product> listOfOrdinaryProducts;

    private static final String removeString = "Remove";
    private static final String saveString = "Save";
    private static final String loadString = "Load";
    private static final String viewString = "View Info";
    private JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton viewButton;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/shoppingCart.json";
    private final ImageIcon theOrdinaryLogo = new ImageIcon("model/theOrdinaryLogo.png");
    private static final int WIDTH = MadeForMeSkinCareAppGUI.WIDTH;
    private static final int HEIGHT = MadeForMeSkinCareAppGUI.HEIGHT;
    private Component theParent;

    //EFFECTS: constructs shopping cart panel
    public ShoppingCartUI(MadeForMeSkinCareAppGUI ui, Component parent) {
        super("Shopping Cart", false, false, false, false);
        shoppingCart = ui.getShoppingCart();
        theParent = parent;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setSize(WIDTH / 2 + 10, HEIGHT - 50);
        setPosition(parent);
        setVisible(true);

        TheOrdinaryProducts theOrdinaryProducts = new TheOrdinaryProducts();
        listOfOrdinaryProducts = theOrdinaryProducts.getListOfTheOrdinaryProducts();
        shopper = ui.getShoppingCart().getShopper();

        cartListModel = new DefaultListModel();

        cartList = new JList(cartListModel);
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartList.setSelectedIndex(0);
        cartList.addListSelectionListener(this);
        cartList.setVisibleRowCount(shoppingCart.getProductsInCart().size() + 1);
        JScrollPane listScrollPane = new JScrollPane(cartList);

        createButtons(listScrollPane);
        addButtons(listScrollPane);

    }

    //MODIFIES: this
    //EFFECTS: creates buttons (remove, save and load) related to shopping cart
    public void createButtons(JScrollPane listScrollPane) {
        //REMOVE BUTTON
        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveProduct());
        removeButton.setEnabled(false);

        //SAVE BUTTON
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveCart());
        saveButton.setEnabled(true);

        //LOAD BUTTON
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadFile());
        loadButton.setEnabled(true);

        //VIEW BUTTON
        viewButton = new JButton(viewString);
        viewButton.setActionCommand(viewString);
        viewButton.addActionListener(new ViewProductFromShoppingCart());
        viewButton.setEnabled(false);
    }

    //MODIFIES: this
    //EFFECTS: adds button onto shopping cart panel
    public void addButtons(JScrollPane listScrollPane) {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(removeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(saveButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(loadButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(viewButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    //MODIFIES: this
    //EFFECTS: adds products from recommendation list to shopping cart, and enables remove and load buttons
    public void addProductToList(Product p) {
        cartListModel.addElement(p.getProductName());
        removeButton.setEnabled(true);
        loadButton.setEnabled(false);
        viewButton.setEnabled(true);
        cartList.setSelectedIndex(shoppingCart.getProductsInCart().size() - 1);
    }

    //MODIFIES: this
    //EFFECTS: creates action for the "remove" button, removes product from cart and decrements list index
    class RemoveProduct implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = cartList.getSelectedIndex();
            cartListModel.remove(index);
            Product product = shoppingCart.getProductsInCart().get(index);
            shoppingCart.removeProductFromCart(product);

            int size = cartListModel.getSize();

            if (size == 0) {
                removeButton.setEnabled(false);
                loadButton.setEnabled(true);

            } else {
                if (index == cartListModel.getSize()) {
                    index--;
                }

                cartList.setSelectedIndex(index);
                cartList.ensureIndexIsVisible(index);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: creates action for the "save" button; saves shopping cart to JSON file
    // catches FileNotFoundException and displays dialog box to alert user
    class SaveCart implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = cartList.getSelectedIndex();

            try {
                jsonWriter.open();
                jsonWriter.writeShoppingCart(shoppingCart);
                jsonWriter.close();
            } catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(null,
                        "File Not Found.",
                        "Unable to write to file: " + JSON_STORE,
                        JOptionPane.ERROR_MESSAGE);
            }

            cartList.setSelectedIndex(index);
            cartList.ensureIndexIsVisible(index);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads the shopping cart from JSON file and puts it in the shopping cart frame; catches IOException
    // and displays dialog box to alert user of exception
    class LoadFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                parseFileToShoppingCart();
                shopper = jsonReader.read().getShopper();
                cartListModel.removeAllElements();
                int index = 0;
                for (Product p : shoppingCart.getProductsInCart()) {
                    cartListModel.insertElementAt(p.getProductName(), index);
                    cartList.setSelectedIndex(index);
                    cartList.ensureIndexIsVisible(index);
                    index++;
                }
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null,
                        "Unable to read from file: " + JSON_STORE,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            loadButton.setEnabled(false);
            if (cartListModel.size() > 0) {
                viewButton.setEnabled(true);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: helper method for load action; reads file and adds product from file to shopping cart
    public void parseFileToShoppingCart() throws IOException {
        ShoppingCart fileToLoad = jsonReader.read();
        for (Product p : fileToLoad.getProductsInCart()) {
            shoppingCart.addProductToCart(p);
        }
    }

    //MODIFIES: this
    //EFFECTS: creates action for the view button; shows dialog box with product name, description, ingredient list,
    // and price
    class ViewProductFromShoppingCart implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = cartList.getSelectedIndex();
            JOptionPane.showMessageDialog(null,
                    shoppingCart.getProductsInCart().get(index).getProductName()
                            + ": \n" + shoppingCart.getProductsInCart().get(index).getDescription()
                            + "\nIngredient Lists: "
                            + shoppingCart.getProductsInCart().get(index).getIngredients()
                            + "\nPriced at $" + shoppingCart.getProductsInCart().get(index).getPrice() + "0",
                    "Product Information",
                    JOptionPane.INFORMATION_MESSAGE, theOrdinaryLogo);

            int size = cartListModel.getSize();
            if (size == 0) {
                viewButton.setEnabled(false);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: Sets the position of this remote control UI relative to parent component
    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth(), 0);
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (cartList.getSelectedIndex() == -1) {
                removeButton.setEnabled(false);

            } else {
                removeButton.setEnabled(true);
            }
        }
    }

}