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

    private JList list;
    private DefaultListModel listModel;

    private Shopper shopper;
    private ShoppingCart shoppingCart;
    private List<Product> listOfOrdinaryProducts;

    private static final String removeString = "Remove";
    private static final String saveString = "Save";
    private static final String loadString = "Load";
    private JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/shoppingCart.json";
    private static final int WIDTH = MadeForMeSkinCareAppUI.WIDTH;
    private static final int HEIGHT = MadeForMeSkinCareAppUI.HEIGHT;
    private Component theParent;


    public ShoppingCartUI(ShoppingCart sc, Component parent) {
        super("Shopping Cart", false, false, false, false);
        shoppingCart = sc;
        theParent = parent;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setSize(WIDTH / 2 + 10, HEIGHT - 50);
        setPosition(parent);
        setVisible(true);

        TheOrdinaryProducts theOrdinaryProducts = new TheOrdinaryProducts();
        listOfOrdinaryProducts = theOrdinaryProducts.getListOfTheOrdinaryProducts();
        shopper = sc.getShopper();

        listModel = new DefaultListModel();

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(shoppingCart.getProductsInCart().size() + 1);
        JScrollPane listScrollPane = new JScrollPane(list);

        addButtons(listScrollPane);

    }

    public void addButtons(JScrollPane listScrollPane) {
        //REMOVE BUTTON
        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveProduct());
        removeButton.setEnabled(false);

        //TODO:3
        //SAVE BUTTON
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveCart());
        saveButton.setEnabled(true);

        //TODO:4
        //LOAD BUTTON
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadFile());
        loadButton.setEnabled(true);

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(removeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(saveButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(loadButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    public void addProductToList(Product p, int index) {
        listModel.insertElementAt(p.getProductName(), index);
        list.setSelectedIndex(index);
        list.ensureIndexIsVisible(index);
        makeButtonsVisible();
    }

    private void makeButtonsVisible() {
        removeButton.setEnabled(true);
        loadButton.setEnabled(true);
    }

    class RemoveProduct implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }


    class SaveCart implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();

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

            int size = listModel.getSize();

            //if (size == 0) { //Nobody's left, disable firing.
              //  saveButton.setEnabled(false);

            //}

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }
    }

    //EFFECTS: loads the shopping cart from file and puts it in the shopping cart frame
    class LoadFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //int index = list.getSelectedIndex();

            try {
                shoppingCart = jsonReader.read();
                //TODO: make it so shopping cart is replaced
                listModel.removeAllElements();
                for (Product p : shoppingCart.getProductsInCart()) {
                    int index = 0;
                    listModel.insertElementAt(p.getProductName(), index);
                    list.setSelectedIndex(index);
                    list.ensureIndexIsVisible(index);
                    index++;
                }


            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null,
                        "Unable to read from file: " + JSON_STORE,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    /**
     * Sets the position of this remote control UI relative to parent component
     *
     * @param parent the parent component
     */
    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth(), 0);
    }


    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                removeButton.setEnabled(true);
            }
        }
    }

}

